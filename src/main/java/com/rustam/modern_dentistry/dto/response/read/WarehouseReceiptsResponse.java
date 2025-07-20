package com.rustam.modern_dentistry.dto.response.read;

import com.rustam.modern_dentistry.dao.entity.enums.status.PendingStatus;
import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseReceiptsResponse {

    Long id;
    LocalDate date;
    LocalTime time;
    Room room;
    String personWhoPlacedOrder;
    Long orderQuantity;
    Long sendQuantity;
    PendingStatus pendingStatus;
}
