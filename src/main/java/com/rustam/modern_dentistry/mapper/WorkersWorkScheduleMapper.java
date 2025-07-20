package com.rustam.modern_dentistry.mapper;

import com.rustam.modern_dentistry.dao.entity.WorkersWorkSchedule;
import com.rustam.modern_dentistry.dto.response.read.WorkersWorkScheduleResponse;
import com.rustam.modern_dentistry.dto.response.update.WorkersWorkScheduleUpdateDTO;
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
public interface WorkersWorkScheduleMapper {
    List<WorkersWorkScheduleResponse> toDtos(List<WorkersWorkSchedule> workersWorkSchedules);

    WorkersWorkScheduleUpdateDTO toDto(WorkersWorkSchedule workersWorkSchedule);
}
