package com.rustam.modern_dentistry.mapper;

import com.rustam.modern_dentistry.dao.entity.PatientBlacklist;
import com.rustam.modern_dentistry.dao.entity.settings.BlacklistResult;
import com.rustam.modern_dentistry.dao.entity.users.Patient;
import com.rustam.modern_dentistry.dto.response.excel.PatientBlacklistExcel;
import com.rustam.modern_dentistry.dto.response.read.PatBlacklistReadRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientBlacklistMapper {
    @Mapping(target = "id", ignore = true)
    PatientBlacklist toEntity(BlacklistResult blacklistResult, Patient patient);

    default PatBlacklistReadRes toReadDto(PatientBlacklist patientBlacklist) {

        return PatBlacklistReadRes.builder()
                .id(patientBlacklist.getId())
                .fullName(patientBlacklist.getPatient().getName() + " " + patientBlacklist.getPatient().getSurname())
                .finCode(patientBlacklist.getPatient().getFinCode())
                .mobilePhone(patientBlacklist.getPatient().getPhone())
                .addedDate(patientBlacklist.getCreatedDate())
                .blacklistReason(patientBlacklist.getBlacklistResult() == null ?
                        "" : patientBlacklist.getBlacklistResult().getStatusName())
                .build();
    }

    default PatientBlacklistExcel toExcelDto(PatientBlacklist patientBlacklist) {

        return PatientBlacklistExcel.builder()
                .fullName(patientBlacklist.getPatient().getName() + " " + patientBlacklist.getPatient().getSurname())
                .finCode(patientBlacklist.getPatient().getFinCode())
                .mobilePhone(patientBlacklist.getPatient().getPhone())
                .addedDate(patientBlacklist.getCreatedDate())
                .blacklistReason(patientBlacklist.getBlacklistResult() == null ?
                        "" : patientBlacklist.getBlacklistResult().getStatusName())
                .build();
    }
}
