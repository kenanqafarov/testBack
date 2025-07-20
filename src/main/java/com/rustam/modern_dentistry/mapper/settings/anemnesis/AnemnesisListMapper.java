package com.rustam.modern_dentistry.mapper.settings.anemnesis;

import com.rustam.modern_dentistry.dao.entity.settings.anamnesis.AnamnesisList;
import com.rustam.modern_dentistry.dto.request.create.AnemnesisListCreateReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateAnemnesisListReq;
import com.rustam.modern_dentistry.dto.response.read.AnemnesisListReadResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AnemnesisListMapper {
    AnemnesisListMapper ANAMNESIS_LIST_MAPPER = Mappers.getMapper(AnemnesisListMapper.class);

    @Mapping(target = "status", defaultValue = "ACTIVE")
    AnamnesisList toEntity(AnemnesisListCreateReq request);

    AnemnesisListReadResponse toReadDto(AnamnesisList entity);

    void updateAnemnesisList(@MappingTarget AnamnesisList entity, UpdateAnemnesisListReq request);
}
