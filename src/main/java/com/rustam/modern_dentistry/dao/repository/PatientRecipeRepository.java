package com.rustam.modern_dentistry.dao.repository;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientRecipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRecipeRepository extends JpaRepository<PatientRecipe, Long> {

    @EntityGraph(attributePaths = {"recipe", "recipe.medicines"})
    List<PatientRecipe> findByPatient_Id(Long patientId);

    @EntityGraph(attributePaths = {"recipe", "recipe.medicines"})
    Optional<PatientRecipe> findById(Long id);
}
