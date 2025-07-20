package com.rustam.modern_dentistry.util.specification.settings;

import com.rustam.modern_dentistry.dao.entity.settings.product.Category;
import com.rustam.modern_dentistry.dao.entity.settings.product.Product;
import com.rustam.modern_dentistry.dto.request.read.ProductCategorySearchRequest;
import com.rustam.modern_dentistry.dto.request.read.ProductSearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> filterBy(ProductSearchRequest request) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }
            if (request.getProductName() != null && !request.getProductName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("productName")), "%" + request.getProductName().toLowerCase() + "%"));
            }
            if (request.getProductNo() != null) {
                predicates.add(cb.equal(root.get("productNo"), request.getProductNo()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
