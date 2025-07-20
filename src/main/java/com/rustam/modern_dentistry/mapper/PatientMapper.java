package com.rustam.modern_dentistry.mapper;

import com.rustam.modern_dentistry.dao.entity.users.Doctor;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dto.response.excel.PatientExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.PatientReadResponse;
import com.rustam.modern_dentistry.dto.response.update.PatientUpdateResponse;
import org.mapstruct.*;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PatientMapper {
    PatientUpdateResponse toUpdatePatient(Patient patient);

    List<PatientReadResponse> toDtos(List<Patient> users);

    @Mapping(target = "doctorName", expression = "java(getDoctorName(user))")
    @Mapping(target = "isBlocked", expression = "java(getBlacklist(user))")
    PatientExcelResponse toExcelDto(Patient user);

    @Mapping(target = "isBlocked", expression = "java(patient.getPatientBlacklist() != null)")
    @Mapping(target = "doctorId", expression = "java(mapDoctorToString(patient.getDoctor()))")
    PatientReadResponse toRead(Patient patient);

    @Named("mapDoctorToString")
    default String mapDoctorToString(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        return doctor.getId(); 
    }


    default String getDoctorName(Patient patient) {
        return patient.getDoctor().getName() + " " + patient.getDoctor().getSurname();
    }

    default Boolean getBlacklist(Patient patient) {
        return patient.getPatientBlacklist() != null;
    }
}
