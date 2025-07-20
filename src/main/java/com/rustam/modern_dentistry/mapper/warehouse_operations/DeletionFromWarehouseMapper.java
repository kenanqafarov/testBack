package com.rustam.modern_dentistry.mapper.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.DeletionFromWarehouse;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.DeletionFromWarehouseProduct;
import com.rustam.modern_dentistry.dto.response.read.DeletionFromWarehouseProductResponse;
import com.rustam.modern_dentistry.dto.response.read.DeletionFromWarehouseReadResponse;
import com.rustam.modern_dentistry.dto.response.read.DeletionFromWarehouseResponse;
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
public interface DeletionFromWarehouseMapper {

    DeletionFromWarehouseResponse toDto(DeletionFromWarehouse deletionFromWarehouse);

    List<DeletionFromWarehouseResponse> toDtos(List<DeletionFromWarehouse> all);

    DeletionFromWarehouseProductResponse toResponse(DeletionFromWarehouseProduct deletionFromWarehouseProduct);
}
