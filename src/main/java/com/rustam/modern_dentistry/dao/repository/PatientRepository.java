package com.rustam.modern_dentistry.dao.repository;

import com.rustam.modern_dentistry.dao.entity.users.Patient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
    @Query("SELECT p FROM Patient p WHERE p.doctor.id = :doctorId")
    List<Patient> findAllByDoctor_Id(UUID doctorId);

    @EntityGraph(attributePaths = {"reservations", "generalCalendars", "examinations"})
    Optional<Patient> findById(Long id);

    @EntityGraph(attributePaths = {"doctor"})
    List<Patient> findAll();

    boolean existsByEmailOrFinCode(String email, String finCode);
}
