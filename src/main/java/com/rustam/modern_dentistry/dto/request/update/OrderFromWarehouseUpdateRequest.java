package com.rustam.modern_dentistry.dto.request.update;

import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import com.rustam.modern_dentistry.dto.request.read.OrderFromWarehouseProductRequest;
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
public class OrderFromWarehouseUpdateRequest {

    Long id;

    LocalDate date;

    LocalTime time;

    Room room;

    List<OrderFromWarehouseProductUpdateRequest> orderFromWarehouseProductUpdateRequests;

    String description;
}
