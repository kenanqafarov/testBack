package com.rustam.modern_dentistry.mapper.settings.permission;

import com.rustam.modern_dentistry.dao.entity.settings.permission.Permission;
import com.rustam.modern_dentistry.dto.response.read.InfoPermissionResponse;
import com.rustam.modern_dentistry.dto.response.read.PermissionResponse;
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
public interface PermissionMapper {
    List<PermissionResponse> toDtos(List<Permission> all);

    InfoPermissionResponse toDto(Permission permission);

    PermissionResponse toResponse(Permission permission);
}
