package com.rustam.modern_dentistry.mapper;

import com.rustam.modern_dentistry.dao.entity.GeneralCalendar;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dto.response.create.NewAppointmentResponse;
import com.rustam.modern_dentistry.dto.response.read.PatientReadResponse;
import com.rustam.modern_dentistry.dto.response.read.SelectingDoctorViewingPatientResponse;
import com.rustam.modern_dentistry.dto.response.read.SelectingPatientToReadResponse;
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
public interface GeneralCalendarMapper {

    NewAppointmentResponse toCreate(GeneralCalendar generalCalendar);

    SelectingDoctorViewingPatientResponse toDto(GeneralCalendar generalCalendar);

    List<SelectingDoctorViewingPatientResponse> toResponse(List<GeneralCalendar> allByDoctor);

    SelectingPatientToReadResponse toRead(GeneralCalendar calendar);
}
