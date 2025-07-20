package com.rustam.modern_dentistry.dto.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.VALIDATION_BLACKLIST_ID_REQUIRED;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class PatBlacklistUpdateReq {
    @NotNull(message = VALIDATION_BLACKLIST_ID_REQUIRED)
    Long blacklistId;
}
