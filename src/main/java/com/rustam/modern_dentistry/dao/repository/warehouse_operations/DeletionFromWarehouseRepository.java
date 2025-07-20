package com.rustam.modern_dentistry.dao.repository.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.DeletionFromWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeletionFromWarehouseRepository extends JpaRepository<DeletionFromWarehouse,Long>, JpaSpecificationExecutor<DeletionFromWarehouse> {
}
