package com.rustam.modern_dentistry.dao.repository.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseReceipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WarehouseReceiptsRepository extends JpaRepository<WarehouseReceipts,Long>, JpaSpecificationExecutor<WarehouseReceipts> {
    Optional<WarehouseReceipts> findByGroupId(String groupId);
}
