package com.rustam.modern_dentistry.mapper.settings.teeth;

import com.rustam.modern_dentistry.dao.entity.settings.teeth.TeethExamination;
import com.rustam.modern_dentistry.dto.response.create.TeethExaminationResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface TeethExaminationMapper {
    TeethExaminationResponse toTeethExaminationResponse(TeethExamination teethExamination);

    List<TeethExaminationResponse> toTeethExaminationResponses(List<TeethExamination> teethExaminations);
}
