package com.rustam.modern_dentistry.mapper.settings.recipes;

import com.rustam.modern_dentistry.dao.entity.settings.recipes.Medicine;
import com.rustam.modern_dentistry.dto.request.create.MedicineCreateRequest;
import com.rustam.modern_dentistry.dto.request.update.MedicineUpdateRequest;
import com.rustam.modern_dentistry.dto.response.excel.MedicineExcelResponse;
import com.rustam.modern_dentistry.dto.response.read.MedicineReadResponse;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MedicineMapper {

    Medicine toEntity(MedicineCreateRequest request);

    @Mapping(target = "recipeId", source = "recipe.id")
    MedicineReadResponse toReadDto(Medicine medicine);

    void update(@MappingTarget Medicine medicine, MedicineUpdateRequest request);

    MedicineExcelResponse toExcelDto(Medicine list);
}
