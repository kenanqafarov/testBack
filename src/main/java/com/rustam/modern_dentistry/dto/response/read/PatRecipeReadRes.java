package com.rustam.modern_dentistry.dto.response.read;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PatRecipeReadRes {
    Long id;
    Long recipeId;
    Long patientId;
    String recipeName;
    LocalDate date;
    List<MedicineReadResponse> medicines;
}
