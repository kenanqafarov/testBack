package com.rustam.modern_dentistry.util.specification.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Ceramic;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.util.specification.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

public class CeramicSpecification {
    public static Specification<Ceramic> filterBy(SearchByNameAndStatus request) {

        return new SpecificationBuilder<Ceramic>()
                .addLike("name", request.getName())
                .addLike("status", request.getStatus())
                .build();
    }
}
