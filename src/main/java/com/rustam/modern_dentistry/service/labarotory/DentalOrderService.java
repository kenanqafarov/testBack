package com.rustam.modern_dentistry.service.labarotory;

import com.rustam.modern_dentistry.dao.entity.laboratory.DentalOrder;
import com.rustam.modern_dentistry.dao.entity.laboratory.DentalOrderToothDetail;
import com.rustam.modern_dentistry.dao.entity.settings.Ceramic;
import com.rustam.modern_dentistry.dao.entity.settings.Color;
import com.rustam.modern_dentistry.dao.entity.settings.Metal;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dao.repository.laboratory.DentalOrderRepository;
import com.rustam.modern_dentistry.dao.repository.settings.CeramicRepository;
import com.rustam.modern_dentistry.dao.repository.settings.ColorRepository;
import com.rustam.modern_dentistry.dao.repository.settings.MetalRepository;
import com.rustam.modern_dentistry.dto.request.DentalOrderCreateReq;
import com.rustam.modern_dentistry.dto.request.create.DentalOrderToothDetailIds;
import com.rustam.modern_dentistry.dto.request.update.UpdateLabOrderStatus;
import com.rustam.modern_dentistry.dto.request.update.UpdateOrderPrice;
import com.rustam.modern_dentistry.dto.request.update.UpdateTechnicianOrderReq;
import com.rustam.modern_dentistry.dto.response.read.TechnicianOrderResponse;
import com.rustam.modern_dentistry.exception.custom.CustomError;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.laboratory.DentalOrderMapper;
import com.rustam.modern_dentistry.service.DoctorService;
import com.rustam.modern_dentistry.service.FileService;
import com.rustam.modern_dentistry.service.TechnicianService;
import com.rustam.modern_dentistry.service.settings.teeth.TeethService;
import com.rustam.modern_dentistry.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.rustam.modern_dentistry.util.constants.Directory.pathDentalOrder;

@Service
@RequiredArgsConstructor
public class DentalOrderService {
    private final UtilService utilService;
    private final FileService fileService;
    private final TeethService teethService;
    private final DoctorService doctorService;
    private final TechnicianService technicianService;
    private final DentalOrderMapper dentalOrderMapper;
    private final DentalOrderRepository dentalOrderRepository;
    private final ColorRepository colorRepository;
    private final MetalRepository metalRepository;
    private final CeramicRepository ceramicRepository;

    @Transactional
    public void create(DentalOrderCreateReq request, List<MultipartFile> files) {
        List<String> imagePaths = new ArrayList<>();
        try {
            var entity = dentalOrderMapper.toEntity(request);
            var teeth = teethService.findAllById(request.getTeethList());
            var doctor = doctorService.findById(request.getDoctorId());
            var technician = technicianService.getTechnicianById(request.getTechnicianId());
            var patient = utilService.findByPatientId(request.getPatientId());
            var toothDetails = getToothDetails(request.getToothDetailIds(), entity);
            entity.setTeethList(teeth);
            entity.setDoctor(doctor);
            entity.setTechnician(technician);
            entity.setPatient(patient);
            entity.setOrderDentureInfo(request.getOrderDentureInfo()); // TODO check elemeyi yaz
            entity.setToothDetails(toothDetails);

            if (files != null && !files.isEmpty() && files.stream().anyMatch(file -> !file.isEmpty())) {
                files.forEach(file -> {
                    var newFileName = getNewFileName(file, patient);
                    imagePaths.add(newFileName);
                    fileService.writeFile(file, pathDentalOrder, newFileName);
                });
            }
            entity.setImagePaths(imagePaths);
            dentalOrderRepository.save(entity);
        } catch (Exception ex) {
            // if error happens remove files
            imagePaths.forEach(path -> {
                var fullPath = pathDentalOrder + "/" + path;
                fileService.deleteFile(fullPath);
            });
            throw new CustomError(ex.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<TechnicianOrderResponse> read() {
        // 1. First, load orders without toothDetails and teethDetails
        List<DentalOrder> orders = dentalOrderRepository.findAllWithBasicRelations();

        // 2. Then, load toothDetails (this will enrich the same objects) and teethDetails
        // To avoid the "cannot simultaneously fetch multiple bags" error
        if (!orders.isEmpty()) {
            dentalOrderRepository.fetchToothDetails(orders);
            dentalOrderRepository.fetchTeethList(orders);
        }

        return orders.stream().map(dentalOrderMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public TechnicianOrderResponse readById(Long id) {
        var order = dentalOrderRepository.findByIdWithBasicRelations(id).orElseThrow(
                () -> new NotFoundException("Dental Order Not Found")
        );
        dentalOrderRepository.fetchTeethList(order);
        dentalOrderRepository.fetchToothDetails(order);
        return dentalOrderMapper.toResponse(order);
    }

    public void updateOrderStatus(UpdateLabOrderStatus request) {
        var dentalOrder = getDentalOrder(request.getId());
        dentalOrder.setDentalWorkStatus(request.getDentalWorkStatus());
    }

    @Transactional(readOnly = true)
    public List<TechnicianOrderResponse> getTechnicianOrdersByUUID() {
        // When the technician logs in, to see their own orders.
        var currentUserId = utilService.getCurrentUserId();
        var user = utilService.findByBaseUserId(currentUserId);

        // 1. First, load imagePaths
        List<DentalOrder> orders = dentalOrderRepository.findAllWithTechnicianId(UUID.fromString(user.getId()));

        // 2. Then, load toothDetails (this will enrich the same objects) and teethDetails
        // To avoid the "cannot simultaneously fetch multiple bags" error
        if (!orders.isEmpty()) {
            dentalOrderRepository.fetchToothDetails(orders);
            dentalOrderRepository.fetchTeethList(orders);
        }

        return orders.stream().map(dentalOrderMapper::toResponse).toList();
    }

    @Transactional
    public void update(Long id, UpdateTechnicianOrderReq request, List<MultipartFile> newFiles) {
        var entity = getDentalOrder(id);

        List<String> originalImagePaths = new ArrayList<>(entity.getImagePaths()); // Existing images
        List<String> tempSavedFiles = new ArrayList<>(); // Keep images temporary
        var updatedImagePaths = new ArrayList<>(originalImagePaths.stream()
                .map(url -> url.substring(url.lastIndexOf("/") + 1))
                .toList());
        try {
            // 1. Remove the images scheduled for deletion from the list, but do not actually delete them from the system yet.
            if (request.getDeleteFiles() != null) {
                var deleteFiles = request.getDeleteFiles().stream()
                        .map(url -> url.substring(url.lastIndexOf("/") + 1))
                        .toList();
                updatedImagePaths.removeAll(deleteFiles);
            }

            // 2. Add new images
            if (newFiles != null && !newFiles.isEmpty() && newFiles.stream().anyMatch(file -> !file.isEmpty())) {
                for (MultipartFile file : newFiles) {
                    String newFileName = getNewFileName(file, entity.getPatient());
                    fileService.writeFile(file, pathDentalOrder, newFileName); // yaz diskinə
                    tempSavedFiles.add(newFileName);
                    updatedImagePaths.add(newFileName);
                }
            }

            // 3. Update Entity
            dentalOrderMapper.updateEntity(entity, request);
            entity.setImagePaths(updatedImagePaths);
            if (request.getToothDetailIds() != null) {

                entity.getToothDetails().clear(); // Köhnələri sil
                var newDetails = getToothDetails(request.getToothDetailIds(), entity);
                entity.getToothDetails().addAll(newDetails); // Yeniləri əlavə et
            }
            dentalOrderRepository.save(entity); // Transaction success

            // 4. Bu nöqtəyə qədər heç bir exception yoxdursa, artıq silinəcək faylları sistemdən sil
            if (request.getDeleteFiles() != null) {
                var deleteFiles = request.getDeleteFiles().stream()
                        .map(url -> url.substring(url.lastIndexOf("/") + 1))
                        .toList();
                deleteFiles.forEach(fileName -> fileService.deleteFile(pathDentalOrder + "/" + fileName));
            }

        } catch (Exception ex) {
            tempSavedFiles.forEach(fileName -> fileService.deleteFile(pathDentalOrder + "/" + fileName));
            throw new CustomError(ex.getMessage());
        }
    }

    public void delete(Long id) {
        var dentalOrder = dentalOrderRepository.findByIdWithBasicRelations(id).orElseThrow(
                () -> new NotFoundException("Dental Order Not Found")
        );
        dentalOrderRepository.delete(dentalOrder);
        dentalOrder.getImagePaths().forEach(
                fileName -> fileService.deleteFile(pathDentalOrder + "/" + fileName)
        );
    }

    public void setOrderPrice(Long id, UpdateOrderPrice request) {
        var dentalOrder = getDentalOrder(id);
        dentalOrder.setPrice(request.getPrice());
        dentalOrderRepository.save(dentalOrder);
    }


    private DentalOrder getDentalOrder(Long id) {
        return dentalOrderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Dental Order Not Found")
        );
    }

    private String getNewFileName(MultipartFile file, Patient patient) {
        return fileService.getNewFileName(file,
                patient.getName() + "_"
                + patient.getSurname() + "_");
    }

    private List<DentalOrderToothDetail> getToothDetails(List<DentalOrderToothDetailIds> request, DentalOrder entity) {
        if (request == null) {
            return null;
        }

        List<DentalOrderToothDetail> toothDetails = new ArrayList<>();

        // Collect all IDs
        Set<Long> colorIds = new HashSet<>();
        Set<Long> metalIds = new HashSet<>();
        Set<Long> ceramicIds = new HashSet<>();

        for (var detailReq : request) {
            colorIds.add(detailReq.getColorId());
            metalIds.add(detailReq.getMetalId());
            ceramicIds.add(detailReq.getCeramicId());
        }

        // Fetch from the DB in bulk
        Map<Long, Color> colorMap = colorRepository.findAllById(colorIds)
                .stream().collect(Collectors.toMap(Color::getId, Function.identity()));
        Map<Long, Metal> metalMap = metalRepository.findAllById(metalIds)
                .stream().collect(Collectors.toMap(Metal::getId, Function.identity()));
        Map<Long, Ceramic> ceramicMap = ceramicRepository.findAllById(ceramicIds)
                .stream().collect(Collectors.toMap(Ceramic::getId, Function.identity()));

        // Now, for each detail, we add the corresponding objects
        for (var detailReq : request) {

            Color color = colorMap.get(detailReq.getColorId());
            if (color == null) {
                throw new NotFoundException("Color not found with id: " + detailReq.getColorId());
            }

            Metal metal = metalMap.get(detailReq.getMetalId());
            if (metal == null) {
                throw new NotFoundException("Metal not found with id: " + detailReq.getMetalId());
            }

            Ceramic ceramic = ceramicMap.get(detailReq.getCeramicId());
            if (ceramic == null) {
                throw new NotFoundException("Ceramic not found with id: " + detailReq.getCeramicId());
            }

            DentalOrderToothDetail detail = new DentalOrderToothDetail();
            detail.setDentalOrder(entity);
            detail.setColor(color);
            detail.setMetal(metal);
            detail.setCeramic(ceramic);
            toothDetails.add(detail);
        }

        return toothDetails;
    }
}
