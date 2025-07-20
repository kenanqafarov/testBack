package com.rustam.modern_dentistry.util.specification.settings.teeth;

import com.rustam.modern_dentistry.dao.entity.settings.teeth.Teeth;
import com.rustam.modern_dentistry.dao.entity.enums.status.ToothLocation;
import com.rustam.modern_dentistry.dao.entity.enums.status.ToothType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TeethSpecification {

    public static Specification<Teeth> filterBy(Long toothNo, ToothType toothType, ToothLocation toothLocation) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //root.fetch("examinations", JoinType.LEFT);

            // Diş nömrəsi (toothNo) üzrə filter
            if (toothNo != null) {
                predicates.add(cb.equal(root.get("toothNo"), toothNo));
            }

            // Diş tipi (toothType) üzrə filter
            if (toothType != null) {
                predicates.add(cb.equal(root.get("toothType"), toothType));
            }

            // Dişin yerləşdiyi yer (toothLocation) üzrə filter
            if (toothLocation != null) {
                predicates.add(cb.equal(root.get("toothLocation"), toothLocation));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
