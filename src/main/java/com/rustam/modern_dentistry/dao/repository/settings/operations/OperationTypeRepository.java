package com.rustam.modern_dentistry.dao.repository.settings.operations;

import com.rustam.modern_dentistry.dto.response.read.OpInsuranceReadResponse;
import com.rustam.modern_dentistry.dao.entity.settings.operations.OpType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OperationTypeRepository extends JpaRepository<OpType, Long>, JpaSpecificationExecutor<OpType> {

    @EntityGraph(attributePaths = {"insurances", "opTypeItems"})
    Optional<OpType> findById(Long id);

    @EntityGraph(attributePaths = {"opTypeItems"})
    List<OpType> findAll();

    @EntityGraph(attributePaths = {"opTypeItems"})
    Page<OpType> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"opTypeItems"})
    Page<OpType> findAll(Specification<OpType> spec, Pageable pageable);


    @Query("""
            select new com.rustam.modern_dentistry.dto.response.read.OpInsuranceReadResponse(
                d.id as insuranceCompanyId,
                d.companyName, 
                i.deductiblePercentage
            ) 
            from InsuranceCompany d 
            left join OpTypeInsurance i ON i.insuranceCompany.id = d.id AND (i.opType.id = :opTypeId OR i.opType.id IS NULL)
            """)
    List<OpInsuranceReadResponse> findByOpTypeId(@Param("opTypeId") Long opTypeId);

    Optional<OpType> findByCategoryName(String operationCategoryName);
}
