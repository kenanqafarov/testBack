package com.rustam.modern_dentistry.dao.repository.settings.anemnesis;

import com.rustam.modern_dentistry.dao.entity.settings.anamnesis.AnamnesisList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnemnesisListRepository extends JpaRepository<AnamnesisList, Long>, JpaSpecificationExecutor<AnamnesisList> {
}
