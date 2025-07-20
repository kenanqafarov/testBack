package com.rustam.modern_dentistry.dto.request.delete;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteAppointmentRequest {
    Long patientId;
    LocalTime time;
}
