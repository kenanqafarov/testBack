package com.rustam.modern_dentistry.mapper.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientPhotos;
import com.rustam.modern_dentistry.dto.request.create.PatPhotosCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatPhotosUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatPhotosReadRes;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientPhotosMapper {

    PatientPhotos toEntity(PatPhotosCreateReq request, String fileName);

    @Mapping(target = "patientId", source = "entity.patient.id")
    PatPhotosReadRes toResponse(PatientPhotos entity, String url);

    void update(@MappingTarget PatientPhotos entity,
                PatPhotosUpdateReq request,
                String fileName
    );
}
