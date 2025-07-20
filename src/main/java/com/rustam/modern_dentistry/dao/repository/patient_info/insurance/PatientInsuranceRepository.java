package com.rustam.modern_dentistry.dao.repository.patient_info.insurance;

import com.rustam.modern_dentistry.dao.entity.patient_info.insurance.PatientInsurance;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance, Long> {
    boolean existsByPolicyNumber(String policyNumber);

    @EntityGraph(attributePaths = {"insuranceCompany", "balances"})
    List<PatientInsurance> findByPatient_Id(Long patientId);

    @Modifying
    @Transactional
    @Query("UPDATE PatientInsurance pi " +
           "SET pi.status = CASE WHEN pi.id = :id THEN " +
           "     (CASE WHEN pi.status = 'ACTIVE' THEN 'PASSIVE' ELSE 'ACTIVE' END) " +
           "     ELSE 'PASSIVE' END " +
           "WHERE pi.patient.id = :patientId AND (pi.status = 'ACTIVE' OR pi.id = :id)")
    void updatePatientInsuranceStatus(@Param("id") Long id, @Param("patientId") Long patientId);

}
