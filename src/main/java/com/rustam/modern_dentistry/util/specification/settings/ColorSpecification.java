package com.rustam.modern_dentistry.util.specification.settings;

import com.rustam.modern_dentistry.dao.entity.settings.Color;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.util.specification.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

public class ColorSpecification {
    public static Specification<Color> filterBy(SearchByNameAndStatus request) {

        return new SpecificationBuilder<Color>()
                .addLike("name", request.getName())
                .addLike("status", request.getStatus())
                .build();
    }
}
