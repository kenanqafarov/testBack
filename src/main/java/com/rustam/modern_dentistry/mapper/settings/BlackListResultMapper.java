package com.rustam.modern_dentistry.mapper.settings;

import com.rustam.modern_dentistry.dao.entity.settings.BlacklistResult;
import com.rustam.modern_dentistry.dto.request.UpdateBlacklistResultReq;
import com.rustam.modern_dentistry.dto.request.create.BlacklistResultCreateReq;
import com.rustam.modern_dentistry.dto.response.excel.BlacklistResultExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.BlacklistResultReadRes;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BlackListResultMapper {
    BlacklistResult toEntity(BlacklistResultCreateReq request);

    BlacklistResultReadRes toReadDto(BlacklistResult entity);

    void update(@MappingTarget BlacklistResult medicine, UpdateBlacklistResultReq request);

    BlacklistResultExcelResponse toExcelDto(BlacklistResult list);
}
