package com.rustam.modern_dentistry.dto.response.excel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rustam.modern_dentistry.dao.entity.enums.status.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ReservationExcelResponse {
    @JsonProperty("Pasient")
    String patient;
    @JsonProperty("Mobil Nömrə")
    String mobilePhone;
    @JsonProperty("Həkim")
    String doctor;
    @JsonProperty("Başlama tarixi")
    LocalDate startDate;
    @JsonProperty("Bitiş tarixi")
    LocalDate endDate;
    @JsonProperty("Başlama Saatı")
    LocalTime startTime;
    @JsonProperty("Bitiş Saatı")
    LocalTime endTime;
    @JsonProperty("Status")
    ReservationStatus status;
}
