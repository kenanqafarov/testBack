package com.rustam.modern_dentistry.util.specification.settings.teeth;

import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItem;
import com.rustam.modern_dentistry.dao.entity.settings.teeth.TeethOperation;
import com.rustam.modern_dentistry.dto.request.read.SearchTeethOperationRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TeethOperationSpecification {

    public static Specification<TeethOperation> filterBy(SearchTeethOperationRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            root.fetch("opTypeItem", JoinType.LEFT);
            if (request.getOperationName() != null && !request.getOperationName().isEmpty()) {
                Join<TeethOperation, OpTypeItem> operationItemJoin = root.join("opTypeItem");
                predicates.add(cb.like(cb.lower(operationItemJoin.get("operationName")), "%" + request.getOperationName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }}
