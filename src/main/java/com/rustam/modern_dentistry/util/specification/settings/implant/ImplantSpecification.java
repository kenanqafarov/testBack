package com.rustam.modern_dentistry.util.specification.settings.implant;

import com.rustam.modern_dentistry.dao.entity.settings.Cabinet;
import com.rustam.modern_dentistry.dao.entity.settings.implant.Implant;
import com.rustam.modern_dentistry.dto.request.read.CabinetSearchRequest;
import com.rustam.modern_dentistry.dto.request.read.ImplantSearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ImplantSpecification {
    public static Specification<Implant> filterBy(ImplantSearchRequest request) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }
            if (request.getImplantBrandName() != null && !request.getImplantBrandName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("implantBrandName")), "%" + request.getImplantBrandName().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
