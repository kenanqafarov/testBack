package com.rustam.modern_dentistry.mapper.settings.anemnesis;

import com.rustam.modern_dentistry.dao.entity.settings.anamnesis.AnamnesisCategory;
import com.rustam.modern_dentistry.dto.request.create.AnemnesisCatCreateReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateAnemnesisCatReq;
import com.rustam.modern_dentistry.dto.response.excel.AnamnesisExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.AnamnesisCatReadResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AnemnesisCategoryMapper {
    AnemnesisCategoryMapper ANAMNESIS_CAT_MAPPER = Mappers.getMapper(AnemnesisCategoryMapper.class);

    @Mapping(target = "status", defaultValue = "ACTIVE")
    AnamnesisCategory toEntity(AnemnesisCatCreateReq request);

    @Mapping(target = "anemnesisListReadResponse", source = "anamnesisList")
    AnamnesisCatReadResponse toReadDto(AnamnesisCategory entity);

    AnamnesisExcelResponse toExcelDto(AnamnesisCategory entity);

    void updateAnemnesisCategory(@MappingTarget AnamnesisCategory entity, UpdateAnemnesisCatReq request);
}

