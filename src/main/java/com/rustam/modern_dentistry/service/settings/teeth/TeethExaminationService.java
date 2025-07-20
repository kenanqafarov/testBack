package com.rustam.modern_dentistry.service.settings.teeth;

import com.rustam.modern_dentistry.dao.entity.Examination;
import com.rustam.modern_dentistry.dao.entity.settings.teeth.Teeth;
import com.rustam.modern_dentistry.dao.entity.settings.teeth.TeethExamination;
import com.rustam.modern_dentistry.dao.repository.settings.teeth.TeethExaminationRepository;
import com.rustam.modern_dentistry.dto.request.create.TeethExaminationRequest;
import com.rustam.modern_dentistry.dto.request.read.TeethExaminationSearchRequest;
import com.rustam.modern_dentistry.dto.request.update.TeethExaminationUpdateRequest;
import com.rustam.modern_dentistry.dto.response.create.TeethExaminationResponse;
import com.rustam.modern_dentistry.exception.custom.ExistsException;
import com.rustam.modern_dentistry.exception.custom.TeethExaminationNotFoundException;
import com.rustam.modern_dentistry.mapper.settings.teeth.TeethExaminationMapper;
import com.rustam.modern_dentistry.service.settings.ExaminationService;
import com.rustam.modern_dentistry.util.specification.settings.teeth.TeethExaminationSpecification;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TeethExaminationService {

    TeethExaminationRepository teethExaminationRepository;
    ExaminationService examinationService;
    TeethService teethService;
    TeethExaminationMapper teethExaminationMapper;

    public TeethExaminationResponse create(TeethExaminationRequest teethExaminationRequest) {
        Teeth teeth = teethService.findById(teethExaminationRequest.getTeethId());
        boolean existsTeethExaminationByExaminationId = teethExaminationRepository.existsTeethExaminationByExamination_IdAndToothNo(teethExaminationRequest.getExaminationId(),teeth.getToothNo());
        if (existsTeethExaminationByExaminationId){
            throw new ExistsException("This examination for this tooth is now available.");
        }
        Examination examination = examinationService.findById(teethExaminationRequest.getExaminationId());
        TeethExamination teethExamination = TeethExamination.builder()
                .teeth(teeth)
                .examination(examination)
                .toothNo(teeth.getToothNo())
                .build();
        teethExaminationRepository.save(teethExamination);
        return teethExaminationMapper.toTeethExaminationResponse(teethExamination);
    }

    public List<TeethExaminationResponse> read() {
        List<TeethExamination> teethExaminations = teethExaminationRepository.findAll();
        return teethExaminationMapper.toTeethExaminationResponses(teethExaminations);
    }

    public List<TeethExaminationResponse> search(TeethExaminationSearchRequest teethExaminationSearchRequest) {
        List<TeethExamination> teethExaminations = teethExaminationRepository.findAll(TeethExaminationSpecification.filterBy(teethExaminationSearchRequest));
        return teethExaminationMapper.toTeethExaminationResponses(teethExaminations);
    }

    @Transactional
    public TeethExaminationResponse update(TeethExaminationUpdateRequest teethExaminationUpdateRequest) {
        TeethExamination teethExamination = findById(teethExaminationUpdateRequest.getId());
        Teeth teeth = teethService.findById(teethExaminationUpdateRequest.getId());
        if (teethExaminationUpdateRequest.getTeethId() != null){
            teethExamination.setTeeth(teeth);
        }
        if (teethExaminationUpdateRequest.getExaminationIds() != null){
            Long examinationId = teethExaminationUpdateRequest.getExaminationIds().stream()
                    .findFirst()
                    .orElse(null);
            boolean existsTeethExaminationByExaminationId = teethExaminationRepository.existsTeethExaminationByExamination_IdAndToothNo(examinationId,teeth.getToothNo());
            if (existsTeethExaminationByExaminationId){
                throw new ExistsException("This examination for this tooth is now available.");
            }
            Examination examination = examinationService.findById(examinationId);
            teethExamination.setExamination(examination);
        }
        teethExaminationRepository.save(teethExamination);
        return teethExaminationMapper.toTeethExaminationResponse(teethExamination);
    }

    private TeethExamination findById(Long id) {
        return teethExaminationRepository.findById(id)
                .orElseThrow(() -> new TeethExaminationNotFoundException("No such dental examination was found."));
    }


    public void delete(Long id) {
        TeethExamination teethExamination = findById(id);
        teethExaminationRepository.delete(teethExamination);
    }
}
