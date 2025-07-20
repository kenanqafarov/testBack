package com.rustam.modern_dentistry.dto.request.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.VALIDATION_LAB_PAYMENT_PRICE;
import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.VALIDATION_TECHNICIAN_ID;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class DentalOrderPaymentCreateReq {
    @NotNull(message = VALIDATION_TECHNICIAN_ID)
    UUID technicianId;
    @NotNull(message = VALIDATION_LAB_PAYMENT_PRICE)
    BigDecimal amount;
}
