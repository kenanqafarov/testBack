package com.rustam.modern_dentistry.service.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientXray;
import com.rustam.modern_dentistry.dao.repository.patient_info.PatientXrayRepository;
import com.rustam.modern_dentistry.dto.request.create.PatXrayCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatXrayUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatXrayReadRes;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.patient_info.PatientXrayMapper;
import com.rustam.modern_dentistry.service.FileService;
import com.rustam.modern_dentistry.util.UtilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.rustam.modern_dentistry.util.constants.Directory.getUrl;
import static com.rustam.modern_dentistry.util.constants.Directory.pathPatXray;

@Service
@RequiredArgsConstructor
public class PatientXrayService {
    private final UtilService utilService;
    private final FileService fileService;
    private final PatientXrayMapper patientXrayMapper;
    private final PatientXrayRepository patientXrayRepository;

    public void create(@Valid PatXrayCreateReq request, MultipartFile file) {
        var patient = utilService.findByPatientId(request.getPatientId());
        var newFileName = fileService.getNewFileName(file, "patient_xray_");
        var entity = patientXrayMapper.toEntity(request, newFileName);
        entity.setPatient(patient);
        fileService.checkFileIfExist(file);
        fileService.writeFile(file, pathPatXray, newFileName);
        patientXrayRepository.save(entity);
    }

    public List<PatXrayReadRes> read(Long patientId) {
        var patientXray = patientXrayRepository.findAllByPatient_Id(patientId);
        return patientXray.stream()
                .map(e -> patientXrayMapper.toResponse(e, getUrl(pathPatXray, e.getFileName())))
                .toList();
    }

    public PatXrayReadRes readById(Long id) {
        var entity = getPatientXray(id);
        return patientXrayMapper.toResponse(entity, getUrl(pathPatXray, entity.getFileName()));
    }

    public void update(Long id, PatXrayUpdateReq request, MultipartFile file) {
        var patientPhoto = getPatientXray(id);
        var newFileName = fileService.getNewFileName(file, "patient_photo_");
        fileService.updateFile(file, pathPatXray, patientPhoto.getFileName(), newFileName);
        patientXrayMapper.update(patientPhoto, request, newFileName);
        patientXrayRepository.save(patientPhoto);
    }

    public void delete(Long id) {
        var patientXray = getPatientXray(id);
        var fullPath = pathPatXray + "/" + patientXray.getFileName();
        patientXrayRepository.delete(patientXray);
        fileService.deleteFile(fullPath);
    }

    private PatientXray getPatientXray(Long id) {
        return patientXrayRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də istifadəçi şəkili tapımadı:" + id)
        );
    }
}
