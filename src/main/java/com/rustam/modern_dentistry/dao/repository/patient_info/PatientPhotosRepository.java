package com.rustam.modern_dentistry.dao.repository.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientPhotos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientPhotosRepository extends JpaRepository<PatientPhotos, Long> {
    List<PatientPhotos> findAllByPatient_Id(Long patientId);
}
