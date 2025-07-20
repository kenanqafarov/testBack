package com.rustam.modern_dentistry.dto.request.create;

import com.rustam.modern_dentistry.dao.entity.enums.WeekDay;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ReservationCreateRequest {

    @NotNull(message = "Başlama tarixi daxil edin")
    LocalDate startDate;
    @NotNull(message = "Bitmə tarixi daxil edin")
    LocalDate endDate;
    @NotNull(message = "Başlama vaxtı daxil edin")
    LocalTime startTime;
    @NotNull(message = "Bitmə vaxtı daxil edin")
    LocalTime endTime;
    @NotNull(message = "Pasienti seçin.")
    UUID doctorId;
    @NotNull(message = "Həkimi seçin.")
    Long patientId;

    Set<WeekDay> weekDays;

    @AssertTrue(message = "Bitmə tarixi başlama tarixindən əvvələ ola bilməz")
    public boolean isValidDateRange() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return !endDate.isBefore(startDate);
    }

    @AssertTrue(message = "Bitmə vaxtı başlama vaxtından əvvələ ola bilməz")
    public boolean isValidTimeRange() {
        if (startTime == null || endTime == null) {
            return true;
        }
        return !endTime.isBefore(startTime);
    }
}
