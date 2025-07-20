package com.rustam.modern_dentistry.dao.repository.settings;

import com.rustam.modern_dentistry.dao.entity.settings.BlacklistResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlacklistResultRepository extends
        JpaRepository<BlacklistResult, Long>,
        JpaSpecificationExecutor<BlacklistResult> {
    boolean existsByStatusNameIgnoreCase(String statusName);
}
