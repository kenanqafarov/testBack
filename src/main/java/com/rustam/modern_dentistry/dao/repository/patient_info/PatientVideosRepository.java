package com.rustam.modern_dentistry.dao.repository.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientVideosRepository extends JpaRepository<PatientVideo, Long> {
    List<PatientVideo> findAllByPatient_Id(Long patientId);
}
