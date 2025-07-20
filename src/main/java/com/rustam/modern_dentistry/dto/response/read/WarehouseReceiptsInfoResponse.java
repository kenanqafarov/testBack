package com.rustam.modern_dentistry.dto.response.read;

import com.rustam.modern_dentistry.dao.entity.enums.status.PendingStatus;
import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import com.rustam.modern_dentistry.dto.OutOfTheWarehouseDto;
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
public class WarehouseReceiptsInfoResponse {

    Long id;
    LocalDate date;
    LocalTime time;
    Room room;
    String personWhoPlacedOrder;
    List<OutOfTheWarehouseDto> outOfTheWarehouseDtos;
    Long orderQuantity;
    Long incomingQuantity;
    PendingStatus pendingStatus;
    String description;
}

