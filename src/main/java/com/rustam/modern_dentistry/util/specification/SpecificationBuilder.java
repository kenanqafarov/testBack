package com.rustam.modern_dentistry.util.specification;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {
    private final List<Specification<T>> specifications = new ArrayList<>();

    public SpecificationBuilder<T> addLike(String field, String value) {
        if (value != null && !value.isEmpty()) {
            specifications.add((root, query, cb) ->
                    cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%")
            );
        }
        return this;
    }

    public SpecificationBuilder<T> addEqual(String field, Object value) {
        if (value != null) {
            specifications.add((root, query, cb) ->
                    cb.equal(root.get(field), value)
            );
        }
        return this;
    }

    public SpecificationBuilder<T> addGreaterThanDate(String field, LocalDate value) {
        if (value != null) {
            specifications.add((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get(field), value)
            );
        }
        return this;
    }

    public SpecificationBuilder<T> addLessThanDate(String field, LocalDate value) {
        if (value != null) {
            specifications.add((root, query, cb) ->
                    cb.lessThan(root.get(field), value)
            );
        }
        return this;
    }

    public Specification<T> build() {
        return specifications.stream()
                .reduce(Specification::and)
                .orElse(null);
    }
}

