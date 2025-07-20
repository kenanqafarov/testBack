package com.rustam.modern_dentistry.util.specification.settings;

import com.rustam.modern_dentistry.dao.entity.Garniture;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.util.specification.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

public class GarnitureSpecification {
    public static Specification<Garniture> filterBy(SearchByNameAndStatus request) {

        return new SpecificationBuilder<Garniture>()
                .addLike("name", request.getName())
                .addLike("status", request.getStatus())
                .build();
    }
}
