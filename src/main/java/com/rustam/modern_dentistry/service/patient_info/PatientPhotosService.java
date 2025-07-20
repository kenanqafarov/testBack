package com.rustam.modern_dentistry.service.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientPhotos;
import com.rustam.modern_dentistry.dao.repository.patient_info.PatientPhotosRepository;
import com.rustam.modern_dentistry.dto.request.create.PatPhotosCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatPhotosUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatPhotosReadRes;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.patient_info.PatientPhotosMapper;
import com.rustam.modern_dentistry.service.FileService;
import com.rustam.modern_dentistry.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.rustam.modern_dentistry.util.constants.Directory.getUrl;
import static com.rustam.modern_dentistry.util.constants.Directory.pathPatPhoto;

@Service
@RequiredArgsConstructor
public class PatientPhotosService {
    private final FileService fileService;
    private final UtilService utilService;
    private final PatientPhotosMapper patientPhotosMapper;
    private final PatientPhotosRepository patientPhotosRepository;

    public void create(PatPhotosCreateReq req, MultipartFile file) {
        var patient = utilService.findByPatientId(req.getPatientId());
        var newFileName = fileService.getNewFileName(file, "patient_photo_");
        var entity = patientPhotosMapper.toEntity(req, newFileName);
        entity.setPatient(patient);
        fileService.checkFileIfExist(file);
        fileService.writeFile(file, pathPatPhoto, newFileName);
        patientPhotosRepository.save(entity);
    }

    public List<PatPhotosReadRes> read(Long patientId) {
        var patientPhotos = patientPhotosRepository.findAllByPatient_Id(patientId);
        return patientPhotos.stream()
                .map(e -> patientPhotosMapper.toResponse(e, getUrl(pathPatPhoto, e.getFileName())))
                .toList();
    }

    public PatPhotosReadRes readById(Long id) {
        var entity = getPatientPhotos(id);
        return patientPhotosMapper.toResponse(entity, getUrl(pathPatPhoto, entity.getFileName()));
    }

    public void update(Long id, PatPhotosUpdateReq request, MultipartFile file) {
        var patientPhoto = getPatientPhotos(id);
        var newFileName = fileService.getNewFileName(file, "patient_photo_");
        fileService.updateFile(file, pathPatPhoto, patientPhoto.getFileName(), newFileName);
        patientPhotosMapper.update(patientPhoto, request, newFileName);
        patientPhotosRepository.save(patientPhoto);
    }

    public void delete(Long id) {
        var patientPhoto = getPatientPhotos(id);
        var fullPath = pathPatPhoto + "/" + patientPhoto.getFileName();
        patientPhotosRepository.delete(patientPhoto);
        fileService.deleteFile(fullPath);
    }

    private PatientPhotos getPatientPhotos(Long id) {
        return patientPhotosRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də istifadəçi şəkili tapımadı:" + id)
        );
    }
}
