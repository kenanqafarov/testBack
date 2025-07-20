package com.rustam.modern_dentistry.service.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientVideo;
import com.rustam.modern_dentistry.dao.repository.patient_info.PatientVideosRepository;
import com.rustam.modern_dentistry.dto.request.create.PatVideosCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatVideosUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatVideosReadRes;
import com.rustam.modern_dentistry.exception.custom.NotFoundException;
import com.rustam.modern_dentistry.mapper.patient_info.PatientVideosMapper;
import com.rustam.modern_dentistry.service.FileService;
import com.rustam.modern_dentistry.util.UtilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.rustam.modern_dentistry.util.constants.Directory.getUrl;
import static com.rustam.modern_dentistry.util.constants.Directory.pathPatVideo;

@Service
@RequiredArgsConstructor
public class PatientVideosService {
    private final UtilService utilService;
    private final FileService fileService;
    private final PatientVideosMapper patientVideosMapper;
    private final PatientVideosRepository patientVideosRepository;

    public void create(@Valid PatVideosCreateReq request, MultipartFile file) {
        var patient = utilService.findByPatientId(request.getPatientId());
        var newFileName = fileService.getNewFileName(file, "patient_video_");
        var entity = patientVideosMapper.toEntity(request, newFileName);
        entity.setPatient(patient);
        fileService.checkFileIfExist(file);
        fileService.checkVideoFile(file);
        fileService.writeFile(file, pathPatVideo, newFileName);
        patientVideosRepository.save(entity);
    }

    public List<PatVideosReadRes> read(Long patientId) {
        var patientXray = patientVideosRepository.findAllByPatient_Id(patientId);
        return patientXray.stream()
                .map(e -> patientVideosMapper.toResponse(e, getUrl(pathPatVideo, e.getFileName())))
                .toList();
    }

    public PatVideosReadRes readById(Long id) {
        var entity = getPatientVideo(id);
        return patientVideosMapper.toResponse(entity, getUrl(pathPatVideo, entity.getFileName()));
    }

    public void update(Long id, PatVideosUpdateReq request, MultipartFile file) {
        var patientPhoto = getPatientVideo(id);
        var newFileName = fileService.getNewFileName(file, "patient_video_");
        fileService.updateFile(file, pathPatVideo, patientPhoto.getFileName(), newFileName);
        patientVideosMapper.update(patientPhoto, request, newFileName);
        patientVideosRepository.save(patientPhoto);
    }

    public void delete(Long id) {
        var patientXray = getPatientVideo(id);
        var fullPath = pathPatVideo + "/" + patientXray.getFileName();
        patientVideosRepository.delete(patientXray);
        fileService.deleteFile(fullPath);
    }

    private PatientVideo getPatientVideo(Long id) {
        return patientVideosRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bu ID-də istifadəçi videosu tapımadı:" + id)
        );
    }
}
