package com.rustam.modern_dentistry.dao.repository.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CabinetRepository extends JpaRepository<Cabinet,Long> , JpaSpecificationExecutor<Cabinet> {
    Optional<Cabinet> findByCabinetName(String cabinetName);

    boolean existsCabinetByCabinetName(String cabinetName);
}
