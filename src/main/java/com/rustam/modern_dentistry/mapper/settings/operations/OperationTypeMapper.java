package com.rustam.modern_dentistry.mapper.settings.operations;

import com.rustam.modern_dentistry.dao.entity.settings.operations.OpType;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeInsurance;
import com.rustam.modern_dentistry.dto.request.create.OpTypeCreateRequest;
import com.rustam.modern_dentistry.dto.request.create.OpTypeInsuranceRequest;
import com.rustam.modern_dentistry.dto.request.update.OpTypeUpdateRequest;
import com.rustam.modern_dentistry.dto.response.excel.OperationTypeExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.OpTypeReadResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OperationTypeMapper {
    OperationTypeMapper OP_TYPE_MAPPER = Mappers.getMapper(OperationTypeMapper.class);

    @Mapping(target = "status", defaultValue = "ACTIVE")
    @Mapping(target = "insurances", ignore = true)
    OpType toEntity(OpTypeCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "opType", ignore = true)
    @Mapping(source = "insuranceCompanyId", target = "insuranceCompany.id")
    OpTypeInsurance toInsuranceEntity(OpTypeInsuranceRequest request);

    @Mapping(target = "insurances", ignore = true)
    @Mapping(target = "opTypeItemCount", expression = "java(countOpTypeItems(entity))")
    OpTypeReadResponse toReadDto(OpType entity);

    @Mapping(target = "insurances", ignore = true)
    OpTypeReadResponse toReadByIdDto(OpType entity);

    @Mapping(target = "opTypeItemCount", expression = "java(countOpTypeItems(entity))")
    OperationTypeExcelResponse toExcelDto(OpType entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "insurances", ignore = true)
    @Mapping(target = "opTypeItems", ignore = true)
    void updateOpType(@MappingTarget OpType entity, OpTypeUpdateRequest request);


    default long countOpTypeItems(OpType entity) {
        return entity.getOpTypeItems().size();
    }
}
