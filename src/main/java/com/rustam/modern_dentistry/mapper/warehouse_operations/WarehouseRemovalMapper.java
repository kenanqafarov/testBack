package com.rustam.modern_dentistry.mapper.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseRemoval;
import com.rustam.modern_dentistry.dto.response.read.WarehouseRemovalReadResponse;
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
public interface WarehouseRemovalMapper {

    List<WarehouseRemovalReadResponse> toDtos(List<WarehouseRemoval> warehouseRemovals);

    WarehouseRemovalReadResponse toDto(WarehouseRemoval warehouseRemoval);
}
