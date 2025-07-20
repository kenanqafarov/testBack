package com.rustam.modern_dentistry.util.specification.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.DeletionFromWarehouse;
import com.rustam.modern_dentistry.dto.request.read.DeletionFromWarehouseSearchRequest;
import org.springframework.data.jpa.domain.Specification;

public class DeletionFromWarehouseSpecification {

    public static Specification<DeletionFromWarehouse> filterBy(DeletionFromWarehouseSearchRequest request) {

        return (root, query, cb) -> {
            Specification<DeletionFromWarehouse> spec = Specification.where(null);

            if (request.getDate() != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("date"), request.getDate()));
            }

            if (request.getTime() != null) {
                spec = spec.and((r, q, c) -> c.equal(r.get("time"), request.getTime()));
            }

            return spec.toPredicate(root, query, cb);
        };
    }
}
