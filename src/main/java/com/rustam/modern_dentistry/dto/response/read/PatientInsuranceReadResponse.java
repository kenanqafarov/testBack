package com.rustam.modern_dentistry.dto.response.read;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PatientInsuranceReadResponse {
    Long id;
    String insuranceCompanyName;
    String policyNumber;
    LocalDate expirationDate;
    BigDecimal deductibleAmount;
    BigDecimal annualMaxAmount;
    Long remainingInsuranceCount;
    Status status;
    String description;
}
