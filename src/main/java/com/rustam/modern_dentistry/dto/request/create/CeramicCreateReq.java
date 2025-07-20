package com.rustam.modern_dentistry.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.VALIDATION_NAME;

@Getter
@Setter
public class CeramicCreateReq {
    @NotBlank(message = VALIDATION_NAME)
    String name;
}
