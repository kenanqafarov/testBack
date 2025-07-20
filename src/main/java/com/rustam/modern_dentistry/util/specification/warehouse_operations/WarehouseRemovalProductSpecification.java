package com.rustam.modern_dentistry.util.specification.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseRemoval;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseRemovalProduct;
import com.rustam.modern_dentistry.dto.request.read.WarehouseRemovalProductSearchRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;

public class WarehouseRemovalProductSpecification {

    public static Specification<WarehouseRemovalProduct> filterBy(WarehouseRemovalProductSearchRequest request) {
        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

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
