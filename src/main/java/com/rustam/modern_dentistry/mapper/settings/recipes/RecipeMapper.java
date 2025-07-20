package com.rustam.modern_dentistry.mapper.settings.recipes;

import com.rustam.modern_dentistry.dao.entity.settings.recipes.Recipe;
import com.rustam.modern_dentistry.dto.request.create.RecipeCreateRequest;
import com.rustam.modern_dentistry.dto.request.update.RecipeUpdateRequest;
import com.rustam.modern_dentistry.dto.response.RecipeReadResponse;
import com.rustam.modern_dentistry.dto.response.excel.RecipeExcelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RecipeMapper {

    Recipe toEntity(RecipeCreateRequest recipe);

    RecipeReadResponse toReadDto(Recipe recipe);

    RecipeExcelResponse toExcelDto(Recipe recipe);

    void update(@MappingTarget Recipe recipe, RecipeUpdateRequest request);
}
