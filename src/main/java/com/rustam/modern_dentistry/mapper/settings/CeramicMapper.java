package com.rustam.modern_dentistry.mapper.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Ceramic;
import com.rustam.modern_dentistry.dto.request.create.CeramicCreateReq;
import com.rustam.modern_dentistry.dto.request.update.UpdateCeramicReq;
import com.rustam.modern_dentistry.dto.response.excel.NameAndStatusExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.CeramicReadRes;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CeramicMapper {
    Ceramic toEntity(CeramicCreateReq request);

    CeramicReadRes toReadDto(Ceramic entity);

    NameAndStatusExcelResponse toExcelDto(Ceramic entity);

    void update(@MappingTarget Ceramic entity, UpdateCeramicReq request);
}
