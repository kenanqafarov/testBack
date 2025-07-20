package com.rustam.modern_dentistry.dao.repository.settings.implant;

import com.rustam.modern_dentistry.dao.entity.settings.implant.ImplantSizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImplantSizeRepository extends JpaRepository<ImplantSizes,Long> , JpaSpecificationExecutor<ImplantSizes> {
}
