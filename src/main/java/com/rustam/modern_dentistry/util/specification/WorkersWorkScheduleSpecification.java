package com.rustam.modern_dentistry.util.specification;

import com.rustam.modern_dentistry.dao.entity.WorkersWorkSchedule;
import com.rustam.modern_dentistry.dao.entity.enums.WeekDay;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class WorkersWorkScheduleSpecification {

    public static Specification<WorkersWorkSchedule> filterBy(WeekDay weekDay) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (weekDay != null) {
                predicates.add(cb.equal(root.get("weekDay"), weekDay));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
