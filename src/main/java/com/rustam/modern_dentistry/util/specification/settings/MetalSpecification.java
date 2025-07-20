package com.rustam.modern_dentistry.util.specification.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Metal;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.util.specification.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

public class MetalSpecification {
    public static Specification<Metal> filterBy(SearchByNameAndStatus request) {

        return new SpecificationBuilder<Metal>()
                .addLike("name", request.getName())
                .addLike("status", request.getStatus())
                .build();
    }
}
