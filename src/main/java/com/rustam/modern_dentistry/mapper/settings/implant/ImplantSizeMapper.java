package com.rustam.modern_dentistry.mapper.settings.implant;

import com.rustam.modern_dentistry.dao.entity.settings.implant.ImplantSizes;
import com.rustam.modern_dentistry.dto.response.read.ImplantSizeResponse;
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
public interface ImplantSizeMapper {
    ImplantSizeResponse toDto(ImplantSizes implantSizes);

    List<ImplantSizeResponse> toDtos(List<ImplantSizes> all);
}
