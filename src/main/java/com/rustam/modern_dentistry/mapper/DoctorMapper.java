package com.rustam.modern_dentistry.mapper;

import com.rustam.modern_dentistry.dao.entity.users.Doctor;
import com.rustam.modern_dentistry.dto.response.create.DoctorCreateResponse;
import com.rustam.modern_dentistry.dto.response.read.DoctorReadResponse;
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
public interface DoctorMapper {
    DoctorCreateResponse toDoctorCreate(Doctor doctor);

   // List<DoctorReadResponse> toReadResponses(List<Doctor> doctors);
}
