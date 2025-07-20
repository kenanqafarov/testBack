package com.rustam.modern_dentistry.dao.repository.settings.anemnesis;

import com.rustam.modern_dentistry.dao.entity.settings.anamnesis.AnamnesisCategory;
import com.rustam.modern_dentistry.dto.request.read.AnemnesisCatSearchReq;
import com.rustam.modern_dentistry.util.specification.settings.anemnesis.AnemnesisCatSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AnemnesisCategoryRepository extends JpaRepository<AnamnesisCategory, Long>, JpaSpecificationExecutor<AnamnesisCategory> {

    @EntityGraph(attributePaths = "anamnesisList")
    Optional<AnamnesisCategory> findById(Long id);

    @EntityGraph(attributePaths = "anamnesisList")
    Page<AnamnesisCategory> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "anamnesisList")
    Page<AnamnesisCategory> findAll(Specification<AnamnesisCategory> spec, Pageable pageable);
}
