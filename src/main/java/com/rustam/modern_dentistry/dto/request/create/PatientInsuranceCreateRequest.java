package com.rustam.modern_dentistry.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PatientInsuranceCreateRequest {
    @NotNull(message = "Sığorta şirkətini seçin.")
    Long insuranceCompanyId;
    @NotBlank(message = "Polis no daxil edin.")
    String policyNumber;
    @NotNull(message = "Bitmə tarixini seçin.")
    LocalDate expirationDate;
    BigDecimal deductibleAmount;
    BigDecimal annualMaxAmount;
    String description;
    Long patientId;
}
