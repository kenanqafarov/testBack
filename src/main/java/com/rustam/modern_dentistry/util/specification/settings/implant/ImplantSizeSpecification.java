package com.rustam.modern_dentistry.util.specification.settings.implant;

import com.rustam.modern_dentistry.dao.entity.settings.implant.Implant;

import com.rustam.modern_dentistry.dao.entity.settings.implant.ImplantSizes;
import com.rustam.modern_dentistry.dto.request.read.ImplantSizeSearchRequest;
import com.rustam.modern_dentistry.dto.request.read.ImplantSizeSearchStatusRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ImplantSizeSpecification {
    public static Specification<ImplantSizes> filterByStatus(ImplantSizeSearchStatusRequest request) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<ImplantSizes> filterBy(ImplantSizeSearchRequest request) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getDiameter() != null) {
                predicates.add(cb.equal(root.get("diameter"), request.getDiameter()));
            }
            if (request.getLength() != null ) {
                predicates.add(cb.equal(root.get("length"), request.getLength()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

    }
}
