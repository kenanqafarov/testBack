package com.rustam.modern_dentistry.dto.request.update;

import com.rustam.modern_dentistry.dto.request.create.WarehouseEntryProductCreateRequest;
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
public class WarehouseEntryUpdateRequest {

    Long id;

    LocalDate date;

    LocalTime time;

    List<WarehouseEntryProductUpdateRequest> warehouseEntryProductUpdateRequests;

    String description;
}
