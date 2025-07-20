package com.rustam.modern_dentistry.dto.request.update;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static com.rustam.modern_dentistry.util.constants.ValidationErrorMessage.VALIDATION_LAB_PAYMENT_PRICE;

@Getter
@Setter
public class UpdateOrderPrice {
    @NotNull(message = VALIDATION_LAB_PAYMENT_PRICE)
    BigDecimal price;
}
