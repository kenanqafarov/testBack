package com.rustam.modern_dentistry.dto.request.update;

import com.rustam.modern_dentistry.dto.request.create.WarehouseRemovalProductCreateRequest;
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
public class WarehouseRemovalProductUpdateRequest {
    String groupId;
    LocalDate date;
    LocalTime time;
    Long warehouseRemovalId;
    List<WarehouseRemovalProductRequest> requests;
    String description;
}
