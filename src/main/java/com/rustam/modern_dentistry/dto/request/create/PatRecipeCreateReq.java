package com.rustam.modern_dentistry.dto.request.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.*;

@Getter
@Setter
public class PatRecipeCreateReq {
    @NotNull(message = VALIDATION_RECIPE_REQUIRED)
    Long recipeId;
    @NotNull(message = VALIDATION_PATIENT_ID)
    Long patientId;
    @NotNull(message = VALIDATION_DATE)
    LocalDate date;
}
