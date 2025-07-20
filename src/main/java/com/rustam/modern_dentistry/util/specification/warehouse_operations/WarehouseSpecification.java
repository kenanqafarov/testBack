package com.rustam.modern_dentistry.util.specification.warehouse_operations;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseEntry;
import com.rustam.modern_dentistry.dto.request.read.WarehouseSearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WarehouseSpecification {

    public static Specification<WarehouseEntry> filterBy(WarehouseSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), request.getCategoryId()));
            }

            if (request.getProductName() != null && !request.getProductName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("productName")), "%" + request.getProductName().toLowerCase() + "%"));
            }

            if (request.getProductNo() != null) {
                predicates.add(cb.equal(root.get("productNo"), request.getProductNo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}