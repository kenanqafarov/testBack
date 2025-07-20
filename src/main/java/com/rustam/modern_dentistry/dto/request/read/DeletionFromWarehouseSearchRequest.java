package com.rustam.modern_dentistry.dto.request.read;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletionFromWarehouseSearchRequest {

    LocalDate date;
    LocalTime time;
}
