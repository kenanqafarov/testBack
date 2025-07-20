package com.rustam.modern_dentistry.mapper.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientXray;
import com.rustam.modern_dentistry.dto.request.create.PatXrayCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatXrayUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatXrayReadRes;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientXrayMapper {
    PatientXray toEntity(PatXrayCreateReq request, String fileName);

    @Mapping(target = "patientId", source = "entity.patient.id")
    PatXrayReadRes toResponse(PatientXray entity, String url);

    void update(@MappingTarget PatientXray entity,
                PatXrayUpdateReq request,
                String fileName
    );
}
