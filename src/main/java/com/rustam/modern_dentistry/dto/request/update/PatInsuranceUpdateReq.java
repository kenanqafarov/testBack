package com.rustam.modern_dentistry.dto.request.update;

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
public class PatInsuranceUpdateReq {
    Long insuranceCompanyId;
    String policyNumber;
    LocalDate expirationDate;
    BigDecimal deductibleAmount;
    BigDecimal annualMaxAmount;
    String description;
}
