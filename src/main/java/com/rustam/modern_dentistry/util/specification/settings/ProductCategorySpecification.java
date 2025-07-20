package com.rustam.modern_dentistry.util.specification.settings;

import com.rustam.modern_dentistry.dao.entity.settings.PriceCategory;
import com.rustam.modern_dentistry.dao.entity.settings.product.Category;
import com.rustam.modern_dentistry.dto.request.read.PriceCategorySearchRequest;
import com.rustam.modern_dentistry.dto.request.read.ProductCategorySearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductCategorySpecification {
    public static Specification<Category> filterBy(ProductCategorySearchRequest request) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }
            if (request.getCategoryName() != null && !request.getCategoryName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("categoryName")), "%" + request.getCategoryName().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
