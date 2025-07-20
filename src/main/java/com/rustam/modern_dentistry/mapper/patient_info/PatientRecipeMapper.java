package com.rustam.modern_dentistry.mapper.patient_info;

import com.rustam.modern_dentistry.dao.entity.patient_info.PatientRecipe;
import com.rustam.modern_dentistry.dao.entity.settings.recipes.Recipe;
import com.rustam.modern_dentistry.dto.request.create.PatRecipeCreateReq;
import com.rustam.modern_dentistry.dto.request.update.PatRecipeUpdateReq;
import com.rustam.modern_dentistry.dto.response.read.PatRecipeReadRes;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PatientRecipeMapper {

    PatientRecipe toEntity(PatRecipeCreateReq request);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "recipeName", source = "recipe.name")
    @Mapping(target = "medicines", source = "recipe.medicines")
    PatRecipeReadRes toDto(PatientRecipe patientRecipe);

    void update(@MappingTarget PatientRecipe patientRecipe, PatRecipeUpdateReq request, Recipe recipe);
}
