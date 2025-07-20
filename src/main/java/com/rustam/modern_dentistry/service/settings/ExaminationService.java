package com.rustam.modern_dentistry.service.settings;

import com.rustam.modern_dentistry.dao.entity.Examination;
import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import com.rustam.modern_dentistry.dao.repository.settings.ExaminationRepository;
import com.rustam.modern_dentistry.dto.request.create.CreateExaminationRequest;
import com.rustam.modern_dentistry.dto.request.create.ExaminationUpdateRequest;
import com.rustam.modern_dentistry.dto.request.read.ExaminationRequest;
import com.rustam.modern_dentistry.dto.response.read.ExaminationResponse;
import com.rustam.modern_dentistry.exception.custom.ExaminationNotFoundException;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.mapper.settings.ExaminationMapper;
import com.rustam.modern_dentistry.util.specification.ExaminationSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ExaminationService {

    ExaminationRepository examinationRepository;
    ExaminationMapper examinationMapper;

    public void createExamination(CreateExaminationRequest createExaminationRequest) {
        boolean existsExaminationByTypeName = existsExaminationByTypeName(createExaminationRequest.getExaminationTypeName());
        if (existsExaminationByTypeName){
            throw new ExistsException("There is such an examination.");
        }
        Examination examination = Examination.builder()
                .typeName(createExaminationRequest.getExaminationTypeName())
                .status(Status.ACTIVE)
                .build();
        examinationRepository.save(examination);
    }

    public boolean existsExaminationByTypeName(String examinationTypeName) {
        return examinationRepository.existsExaminationByTypeName(examinationTypeName);
    }


    public List<ExaminationResponse> read() {
        List<Examination> examinations = examinationRepository.findAll();
        return examinationMapper.toDtos(examinations);
    }

    public List<ExaminationResponse> search(ExaminationRequest examinationRequest) {
        List<Examination> examinations = examinationRepository.findAll(ExaminationSpecification.filterBy(examinationRequest.getTypeName(), examinationRequest.getStatus()));
        return examinationMapper.toDtos(examinations);
    }

    public ExaminationResponse updateStatus(ExaminationRequest examinationRequest) {
        Examination examination = findByTypeName(examinationRequest.getTypeName());
        if (examinationRequest.getTypeName() != null){
            examination.setTypeName(examinationRequest.getTypeName());
        }
        if (examinationRequest.getStatus() != null){
            examination.setStatus(examinationRequest.getStatus());
        }
        examinationRepository.save(examination);
        return examinationMapper.toDto(examination);
    }

    public Examination findByTypeName(String typeName) {
        return examinationRepository.findByTypeName(typeName)
                .orElseThrow(() -> new ExaminationNotFoundException("examination not found"));
    }

    public Examination findById(Long id) {
        return examinationRepository.findById(id)
                .orElseThrow(() -> new ExaminationNotFoundException("examination not found"));
    }

    public void delete(Long id) {
        Examination examination = findById(id);
        examinationRepository.delete(examination);
    }

    public ExaminationResponse update(ExaminationUpdateRequest examinationUpdateRequest) {
        Examination examination = findById(examinationUpdateRequest.getId());
        boolean existsExaminationByTypeName = existsExaminationByTypeName(examinationUpdateRequest.getTypeName());
        if (existsExaminationByTypeName){
            throw new ExistsException("There is such an examination.");
        }
        if (examinationUpdateRequest.getTypeName() != null){
            examination.setTypeName(examinationUpdateRequest.getTypeName());
        }
        examinationRepository.save(examination);
        return examinationMapper.toDto(examination);
    }
}
