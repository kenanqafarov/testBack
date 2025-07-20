package com.rustam.modern_dentistry.util.specification.settings.operations;

import com.rustam.modern_dentistry.dao.entity.settings.operations.OpTypeItem;
import com.rustam.modern_dentistry.dto.request.read.OpTypeItemSearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OpTypeItemSearchSpec {
    public static Specification<OpTypeItem> filterBy(OpTypeItemSearchRequest request) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }
            if (request.getOperationName() != null && !request.getOperationName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("operationName")), "%" + request.getOperationName().toLowerCase() + "%"));
            }
            if (request.getOperationCode() != null && !request.getOperationCode().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("operationCode")), "%" + request.getOperationCode().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
