package com.rustam.modern_dentistry.dao.repository.settings.teeth;


import com.rustam.modern_dentistry.dao.entity.settings.teeth.TeethOperation;
import com.rustam.modern_dentistry.dto.response.read.TeethOperationResponse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeethOperationRepository extends JpaRepository<TeethOperation,Long>, JpaSpecificationExecutor<TeethOperation> {

    @Query("SELECT new com.rustam.modern_dentistry.dto.response.read.TeethOperationResponse(t.id, i.operationName) " +
            "FROM TeethOperation t " +
            "JOIN t.opTypeItem i")
    List<TeethOperationResponse> findAllTeethOperations();

    @EntityGraph(attributePaths = {"teeth"}) // Əlaqəli obyektləri əvvəlcədən yüklə
    List<TeethOperation> findAll(Specification<TeethOperation> spec);
}
