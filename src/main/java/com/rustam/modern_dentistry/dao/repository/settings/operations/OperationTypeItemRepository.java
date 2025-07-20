package com.rustam.modern_dentistry.dao.repository.settings.operations;

import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItem;
import com.rustam.modern_dentistry.dto.response.read.OpTypeItemInsuranceDto;
import com.rustam.modern_dentistry.dto.response.read.OpTypeItemPricesDto;
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

public interface OperationTypeItemRepository extends JpaRepository<OpTypeItem, Long>, JpaSpecificationExecutor<OpTypeItem> {
    @EntityGraph(attributePaths = {"prices"})
    List<OpTypeItem> findAll();

    @EntityGraph(attributePaths = {"prices"})
    List<OpTypeItem> findAll(Specification<OpTypeItem> spec);

    @EntityGraph(attributePaths = {"prices", "insurances"})
    Optional<OpTypeItem> findById(Long id);

    @Query("""
            select new com.rustam.modern_dentistry.dto.response.read.OpTypeItemInsuranceDto(
                d.id as insuranceId,
                d.companyName, 
                i.name, 
                i.amount
            ) 
            from InsuranceCompany d 
            left join OpTypeItemInsurance i 
            ON i.insuranceCompany.id = d.id 
            AND (i.opTypeItem.id = :opTypeItemId OR i.opTypeItem IS NULL)
            """)
    List<OpTypeItemInsuranceDto> findInsurancesByOpTypeItemId(@Param("opTypeItemId") Long opTypeItemId);

    @Query("""
            select new com.rustam.modern_dentistry.dto.response.read.OpTypeItemPricesDto(
                d.name,
                d.id as priceCategoryId,
                i.price
            ) 
            from PriceCategory d 
            left join OpTypeItemPrice i 
            ON i.priceCategory.id = d.id 
            AND (i.opTypeItem.id = :opTypeItemId OR i.opTypeItem IS NULL)
            """)
    List<OpTypeItemPricesDto> findPricesByOpTypeItemId(@Param("opTypeItemId") Long opTypeItemId);

    @Query("SELECT oti FROM OpTypeItem oti " +
           "JOIN FETCH oti.opType ot " +
           "FULL JOIN FETCH oti.prices p " +
           "FULL JOIN FETCH p.priceCategory " +
           "WHERE ot.id = :opTypeId")
    Page<OpTypeItem> findByOpTypeId(@Param("opTypeId") Long opTypeId, Pageable pageable);

}
