package com.rustam.modern_dentistry.mapper.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Metal;
import com.rustam.modern_dentistry.dto.request.create.MetalCreateReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateMetalReq;
import com.rustam.modern_dentistry.dto.response.excel.NameAndStatusExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.MetalReadRes;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MetalMapper {
    Metal toEntity(MetalCreateReq request);

    MetalReadRes toReadDto(Metal entity);

    NameAndStatusExcelResponse toExcelDto(Metal entity);

    void update(@MappingTarget Metal entity, UpdateMetalReq request);
}
