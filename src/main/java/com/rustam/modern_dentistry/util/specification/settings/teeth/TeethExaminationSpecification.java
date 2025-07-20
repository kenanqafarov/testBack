package com.rustam.modern_dentistry.util.specification.settings.teeth;

import com.rustam.modern_dentistry.dao.entity.Examination;
import com.rustam.modern_dentistry.dao.entity.settings.teeth.TeethExamination;
import com.rustam.modern_dentistry.dto.request.read.TeethExaminationSearchRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TeethExaminationSpecification {

    public static Specification<TeethExamination> filterBy(TeethExaminationSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            root.fetch("examination", JoinType.LEFT);
            if (request.getExaminationName() != null && !request.getExaminationName().isEmpty()) {
                Join<TeethExamination, Examination> examinationJoin = root.join("examination");
                predicates.add(cb.like(cb.lower(examinationJoin.get("typeName")), "%" + request.getExaminationName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}