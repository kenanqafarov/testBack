package com.rustam.modern_dentistry.dao.repository.settings;

import com.rustam.modern_dentistry.dao.entity.settings.AppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppointmentTypeRepository extends JpaRepository<AppointmentType,Long> , JpaSpecificationExecutor<AppointmentType> {
    boolean existsByAppointmentTypeName(String appointmentTypeName);
}
