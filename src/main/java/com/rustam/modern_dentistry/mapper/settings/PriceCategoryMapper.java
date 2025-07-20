package com.rustam.modern_dentistry.mapper.settings;

import com.rustam.modern_dentistry.dao.entity.settings.PriceCategory;
import com.rustam.modern_dentistry.dto.request.create.PriceCategoryCreateRequest;
import com.rustam.modern_dentistry.dto.request.update.PriceCategoryUpdateRequest;
import com.rustam.modern_dentistry.dto.response.read.PriceCategoryReadResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PriceCategoryMapper {
    PriceCategoryMapper MAPPER = Mappers.getMapper(PriceCategoryMapper.class);

    @Mapping(target = "status", defaultValue = "ACTIVE")
    PriceCategory toEntity(PriceCategoryCreateRequest request);

    PriceCategoryReadResponse toDto(PriceCategory priceCategory);

    void updateEntity(@MappingTarget PriceCategory priceCategory, PriceCategoryUpdateRequest request);
}
