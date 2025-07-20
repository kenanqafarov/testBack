package com.rustam.modern_dentistry.util.specification.settings.recipes;

import com.rustam.modern_dentistry.dao.entity.settings.recipes.Medicine;
import com.rustam.modern_dentistry.dao.entity.settings.recipes.Recipe;
import com.rustam.modern_dentistry.dto.request.read.MedicineSearchRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MedicineSpecification {

    public static Specification<Medicine> filterBy(MedicineSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getRecipeId() != null) {
                Join<Medicine, Recipe> recipeJoin = root.join("recipe", JoinType.INNER);
                predicates.add(cb.equal(recipeJoin.get("id"), request.getRecipeId()));
            }
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
