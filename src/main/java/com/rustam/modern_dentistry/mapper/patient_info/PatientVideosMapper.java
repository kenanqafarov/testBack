package com.rustam.modern_dentistry.mapper.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientVideo;
import com.rustam.modern_dentistry.dto.request.create.PatVideosCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatVideosUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatVideosReadRes;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientVideosMapper {
    PatientVideo toEntity(PatVideosCreateReq request, String fileName);

    @Mapping(target = "patientId", source = "entity.patient.id")
    PatVideosReadRes toResponse(PatientVideo entity, String url);

    void update(@MappingTarget PatientVideo entity,
                PatVideosUpdateReq request,
                String fileName
    );
}
