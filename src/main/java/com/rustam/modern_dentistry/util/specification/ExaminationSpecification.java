package com.rustam.modern_dentistry.util.specification;

import com.rustam.modern_dentistry.dao.entity.Examination;
import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExaminationSpecification {

    public static Specification<Examination> filterBy(String typeName, Status status) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // TypeName üzrə filter
            if (typeName != null && !typeName.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("typeName")), "%" + typeName.toLowerCase() + "%"));
            }

            // Status üzrə filter
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
