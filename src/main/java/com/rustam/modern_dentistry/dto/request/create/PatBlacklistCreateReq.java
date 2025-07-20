package com.rustam.modern_dentistry.dto.request.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.VALIDATION_BLACKLIST_ID_REQUIRED;
import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.VALIDATION_PATIENT_ID;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class PatBlacklistCreateReq {
    @NotNull(message = VALIDATION_PATIENT_ID)
    Long patientId;
    @NotNull(message = VALIDATION_BLACKLIST_ID_REQUIRED)
    Long blacklistId;
}
