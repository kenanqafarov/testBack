package com.rustam.modern_dentistry.util.specification.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouse;
import com.rustam.modern_dentistry.dto.request.read.OrderFromWarehouseSearchRequest;
import org.springframework.data.jpa.domain.Specification;

public class OrderFromWarehouseSpecification {

    public static Specification<OrderFromWarehouse> filterBy(OrderFromWarehouseSearchRequest request) {

        return (root, query, cb) -> {
            Specification<OrderFromWarehouse> spec = Specification.where(null);

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
