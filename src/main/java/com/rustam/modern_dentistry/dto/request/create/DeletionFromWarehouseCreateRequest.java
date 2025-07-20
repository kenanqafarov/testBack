package com.rustam.modern_dentistry.dto.request.create;

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
public class DeletionFromWarehouseCreateRequest {

    LocalDate date;
    LocalTime time;
    String description;
    List<DeletionFromWarehouseProductRequest> deletionFromWarehouseProductRequests;
}
