package com.rustam.modern_dentistry.util.specification.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseReceipts;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseRemovalProduct;
import com.rustam.modern_dentistry.dto.request.read.WarehouseReceiptsRequest;
import com.rustam.modern_dentistry.dto.request.read.WarehouseRemovalProductSearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class WarehouseReceiptsSpecification {

    public static Specification<WarehouseReceipts> filterBy(WarehouseReceiptsRequest request) {
        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            if (request.getPendingStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("pendingStatus"), request.getPendingStatus()));
            }

            if (request.getDate() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("date"), request.getDate()));
            }

            if (request.getTime() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("time"), request.getTime()));
            }

            return predicate;
        };
    }
}
