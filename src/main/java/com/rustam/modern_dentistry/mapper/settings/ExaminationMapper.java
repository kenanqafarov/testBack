package com.rustam.modern_dentistry.mapper.settings;

import com.rustam.modern_dentistry.dao.entity.Examination;
import com.rustam.modern_dentistry.dto.response.read.ExaminationResponse;
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
public interface ExaminationMapper {
    List<ExaminationResponse> toDtos(List<Examination> examinations);

    ExaminationResponse toDto(Examination examination);
}
