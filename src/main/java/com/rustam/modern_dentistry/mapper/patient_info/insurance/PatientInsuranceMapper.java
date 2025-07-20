package com.rustam.modern_dentistry.mapper.patient_info.insurance;

import com.rustam.modern_dentistry.dao.entity.patient_info.insurance.PatientInsurance;
import com.rustam.modern_dentistry.dao.entity.settings.InsuranceCompany;
import com.rustam.modern_dentistry.dto.request.create.PatientInsuranceCreateRequest;
import com.rustam.modern_dentistry.dto.request.update.PatInsuranceUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatientInsuranceReadResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientInsuranceMapper {
    PatientInsuranceMapper PATIENT_INSURANCE_MAPPER = Mappers.getMapper(PatientInsuranceMapper.class);

    @Mapping(target = "insuranceCompany", ignore = true)
    PatientInsurance toEntity(PatientInsuranceCreateRequest request);

    @Mapping(target = "insuranceCompanyName", source = "patientInsurance.insuranceCompany.companyName")
    @Mapping(target = "remainingInsuranceCount", expression = "java(patientInsurance.getBalances() != null ? patientInsurance.getBalances().size() : 0L)")
    PatientInsuranceReadResponse toReadDto(PatientInsurance patientInsurance);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "insuranceCompany", source = "insuranceCompany")
    @Mapping(target = "deductibleAmount", source = "request.deductibleAmount")
    void updatePatientInsurance(PatInsuranceUpdateReq request,
                                @MappingTarget PatientInsurance entity,
                                InsuranceCompany insuranceCompany);
}
