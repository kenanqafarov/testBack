package com.rustam.modern_dentistry.mapper.settings.implant;

import com.rustam.modern_dentistry.dao.entity.settings.implant.Implant;
import com.rustam.modern_dentistry.dto.response.read.ImplantReadResponse;
import com.rustam.modern_dentistry.dto.response.read.ImplantResponse;
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
public interface ImplantMapper {
    ImplantResponse toDto(Implant implant);

    List<ImplantReadResponse> toDtos(List<Implant> implants);
}
