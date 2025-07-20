package com.rustam.modern_dentistry.dao.repository;

import com.rustam.modern_dentistry.dao.entity.users.Reception;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReceptionRepository extends JpaRepository<Reception, UUID> {
}
