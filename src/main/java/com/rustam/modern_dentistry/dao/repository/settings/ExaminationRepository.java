package com.rustam.modern_dentistry.dao.repository.settings;


import com.rustam.modern_dentistry.dao.entity.Examination;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ExaminationRepository extends JpaRepository<Examination,Long>, JpaSpecificationExecutor<Examination> {

    boolean existsExaminationByTypeName(@NotBlank String examinationTypeName);

    Optional<Examination> findByTypeName(String examinationTypeName);
}
