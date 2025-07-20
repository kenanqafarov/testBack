package com.rustam.modern_dentistry.dto.request.update;

import com.rustam.modern_dentistry.dto.request.create.DeletionFromWarehouseProductRequest;
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
public class DeletionFromWarehouseUpdateRequest {
    Long deletionFromWarehouseId;
    LocalDate date;
    LocalTime time;
    String description;
    List<DeletionFromWarehouseProductRequest> deletionFromWarehouseProductRequests;
}
