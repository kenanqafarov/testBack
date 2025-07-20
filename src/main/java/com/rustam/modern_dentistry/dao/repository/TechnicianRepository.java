package com.rustam.modern_dentistry.dao.repository;

import com.rustam.modern_dentistry.dao.entity.users.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TechnicianRepository extends JpaRepository<Technician, UUID>, JpaSpecificationExecutor<Technician> {
    boolean existsByUsername(String username);
}
