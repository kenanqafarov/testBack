package com.rustam.modern_dentistry.dao.repository.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Ceramic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CeramicRepository extends JpaRepository<Ceramic, Long>, JpaSpecificationExecutor<Ceramic> {
}