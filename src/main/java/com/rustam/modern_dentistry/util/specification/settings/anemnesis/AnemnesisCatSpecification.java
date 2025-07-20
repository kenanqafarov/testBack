package com.rustam.modern_dentistry.util.specification.settings.anemnesis;

import com.rustam.modern_dentistry.dao.entity.settings.anamnesis.AnamnesisCategory;
import com.rustam.modern_dentistry.dto.request.read.AnemnesisCatSearchReq;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnemnesisCatSpecification {
    public static Specification<AnamnesisCategory> filterBy(AnemnesisCatSearchReq request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null && !request.getName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }
            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
