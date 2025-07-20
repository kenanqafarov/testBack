package com.rustam.modern_dentistry.dao.repository.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientAnamnesis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientAnamnesisRepository extends JpaRepository<PatientAnamnesis, Long> {
    List<PatientAnamnesis> findByPatient_Id(Long patientId);
}
