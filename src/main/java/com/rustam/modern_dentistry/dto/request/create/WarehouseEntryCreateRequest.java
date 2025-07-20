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
public class WarehouseEntryCreateRequest {

    LocalDate date;

    LocalTime time;

    List<WarehouseEntryProductCreateRequest> warehouseEntryProductCreateRequests;

    String description;
}
