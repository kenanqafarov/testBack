package com.rustam.modern_dentistry.dao.repository.settings.teeth;


import com.rustam.modern_dentistry.dao.entity.settings.teeth.TeethExamination;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TeethExaminationRepository extends JpaRepository<TeethExamination,Long> , JpaSpecificationExecutor<TeethExamination> {
    boolean existsTeethExaminationByExamination_IdAndToothNo(Long examinationId,Long toothNo);

    @EntityGraph(attributePaths = {"teeth", "examination"}) // Lazy olmadan yükləyir
    Optional<TeethExamination> findById(Long id);

    @EntityGraph(attributePaths = {"teeth", "examination"}) // Əlaqəli obyektləri əvvəlcədən yüklə
    List<TeethExamination> findAll();

    @EntityGraph(attributePaths = {"teeth"}) // Əlaqəli obyektləri əvvəlcədən yüklə
    List<TeethExamination> findAll(Specification<TeethExamination> spec);
}
