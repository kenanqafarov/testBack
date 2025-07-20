package com.rustam.modern_dentistry.dao.repository.settings;

import com.rustam.modern_dentistry.dao.entity.settings.PriceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PriceCategoryRepository extends JpaRepository<PriceCategory, Long>, JpaSpecificationExecutor<PriceCategory> {
    List<PriceCategory> findByIdIn(List<Long> ids);
}
