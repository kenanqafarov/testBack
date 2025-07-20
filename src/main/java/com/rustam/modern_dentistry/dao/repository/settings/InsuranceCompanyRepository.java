package com.rustam.modern_dentistry.dao.repository.settings;

import com.rustam.modern_dentistry.dao.entity.settings.InsuranceCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompany, Long>, JpaSpecificationExecutor<InsuranceCompany> {
    List<InsuranceCompany> findByIdIn(List<Long> ids);

    InsuranceCompany getReferenceById(Long id);
}
