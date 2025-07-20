package com.rustam.modern_dentistry.mapper.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientAnamnesis;
import com.rustam.modern_dentistry.dto.request.create.PatAnamnesisCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatAnamnesisUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatAnamnesisReadRes;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientAnemnesisMapper {
    PatientAnemnesisMapper PATIENT_ANEMNESIS_MAPPER = Mappers.getMapper(PatientAnemnesisMapper.class);

    PatientAnamnesis toEntity(PatAnamnesisCreateReq request);

    @Mapping(target = "patientId", source = "patient.id")
    PatAnamnesisReadRes toReadDto(PatientAnamnesis patient);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addedByName", source = "doctorName")
    @Mapping(target = "addedDateTime", source = "currentDate")
    void updatePatAnemnesis(@MappingTarget PatientAnamnesis entity,
                            PatAnamnesisUpdateReq request,
                            String doctorName,
                            LocalDateTime currentDate
    );
}
