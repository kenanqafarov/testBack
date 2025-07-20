package com.rustam.modern_dentistry.util.specification.settings.permission;

import com.rustam.modern_dentistry.dao.entity.settings.permission.Permission;
import com.rustam.modern_dentistry.dao.entity.settings.product.Category;
import com.rustam.modern_dentistry.dto.request.read.PermissionSearchRequest;
import com.rustam.modern_dentistry.dto.request.read.ProductCategorySearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PermissionSpecification {
    public static Specification<Permission> filterBy(PermissionSearchRequest request) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }
            if (request.getPermissionName() != null && !request.getPermissionName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("permissionName")), "%" + request.getPermissionName().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
