package com.rustam.modern_dentistry.mapper;

import com.rustam.modern_dentistry.dao.entity.Garniture;
import com.rustam.modern_dentistry.dto.request.create.GarnitureCreateReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateGarnitureReq;
import com.rustam.modern_dentistry.dto.response.excel.NameAndStatusExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.GarnitureReadRes;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GarnitureMapper {
    Garniture toEntity(GarnitureCreateReq request);

    GarnitureReadRes toReadDto(Garniture entity);

    NameAndStatusExcelResponse toExcelDto(Garniture entity);

    void update(@MappingTarget Garniture entity, UpdateGarnitureReq request);
}
