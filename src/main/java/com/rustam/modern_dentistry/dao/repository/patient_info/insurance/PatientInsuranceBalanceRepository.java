package com.rustam.modern_dentistry.dao.repository.patient_info.insurance;

import com.rustam.modern_dentistry.dao.entity.patient_info.insurance.PatientInsuranceBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PatientInsuranceBalanceRepository extends JpaRepository<PatientInsuranceBalance, Long> {

    boolean existsByDateAndPatientInsurance_Id(LocalDate date, Long patientInsuranceId);

    List<PatientInsuranceBalance> findAllByPatientInsurance_Id(Long patientInsuranceId);

}
