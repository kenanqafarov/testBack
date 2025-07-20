package com.rustam.modern_dentistry.dao.repository.settings.implant;

import com.rustam.modern_dentistry.dao.entity.settings.implant.Implant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImplantRepository extends JpaRepository<Implant,Long> , JpaSpecificationExecutor<Implant> {
    boolean existsImplantByImplantBrandName(String implantBrandName);
}
