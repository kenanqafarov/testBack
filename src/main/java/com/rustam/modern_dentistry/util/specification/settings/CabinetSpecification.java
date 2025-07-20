package com.rustam.modern_dentistry.util.specification.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Cabinet;
import com.rustam.modern_dentistry.dao.entity.settings.product.Category;
import com.rustam.modern_dentistry.dto.request.read.CabinetSearchRequest;
import com.rustam.modern_dentistry.dto.request.read.ProductCategorySearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CabinetSpecification {
    public static Specification<Cabinet> filterBy(CabinetSearchRequest request) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }
            if (request.getCabinetName() != null && !request.getCabinetName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("cabinetName")), "%" + request.getCabinetName().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
