package com.rustam.modern_dentistry.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.VALIDATION_NAME;

@Getter
@Setter
public class UpdateColorReq {
    @NotBlank(message = VALIDATION_NAME)
    String name;
}
