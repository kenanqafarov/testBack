package com.rustam.modern_dentistry.util.specification.settings.recipes;

import com.rustam.modern_dentistry.dao.entity.settings.recipes.Recipe;
import com.rustam.modern_dentistry.dto.request.read.SearchByNameAndStatus;
import com.rustam.modern_dentistry.util.specification.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

public class RecipeSpecification {
    public static Specification<Recipe> filterBy(SearchByNameAndStatus request) {

        return new SpecificationBuilder<Recipe>()
                .addLike("name", request.getName())
                .addEqual("status", request.getStatus())
                .build();
    }
}
