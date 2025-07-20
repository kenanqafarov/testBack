package com.rustam.modern_dentistry.dao.repository;

import com.rustam.modern_dentistry.dao.entity.Garniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GarnitureRepository extends JpaRepository<Garniture, Long>, JpaSpecificationExecutor<Garniture> {
}
