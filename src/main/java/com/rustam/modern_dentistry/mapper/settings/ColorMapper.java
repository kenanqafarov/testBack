package com.rustam.modern_dentistry.mapper.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Color;
import com.rustam.modern_dentistry.dto.request.create.ColorCreateReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateColorReq;
import com.rustam.modern_dentistry.dto.response.excel.NameAndStatusExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.ColorReadRes;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ColorMapper {
    Color toEntity(ColorCreateReq request);

    ColorReadRes toReadDto(Color entity);

    NameAndStatusExcelResponse toExcelDto(Color entity);

    void update(@MappingTarget Color entity, UpdateColorReq request);
}
