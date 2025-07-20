package com.rustam.modern_dentistry.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.VALIDATION_NAME;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class UpdateBlacklistResultReq {
    @NotBlank(message = VALIDATION_NAME)
    String statusName;
}
