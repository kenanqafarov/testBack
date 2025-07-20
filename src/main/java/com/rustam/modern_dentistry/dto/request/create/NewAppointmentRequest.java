package com.rustam.modern_dentistry.dto.request.create;

import com.rustam.modern_dentistry.dao.entity.enums.status.Appointment;
import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewAppointmentRequest {
    @NotNull
    Room room;
    @NotNull(message = "patient bos ola bilmez")
    Long patientId;

    Appointment appointment;

    List<AppointmentTypeRequestId> appointmentTypeRequestIds;

    LocalDate date;

    LocalTime time;

    LocalTime period;

}
