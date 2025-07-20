package com.rustam.modern_dentistry.dto.response.read;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.OrderFromWarehouse;
import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseRemovalProduct;
import com.rustam.modern_dentistry.dto.OutOfTheWarehouseDto;
import com.rustam.modern_dentistry.dto.response.create.WarehouseRemovalCreateResponse;
import jakarta.persistence.*;
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
public class WarehouseRemovalReadResponse {

    LocalDate date;

    LocalTime time;

    Room room;

    String personWhoPlacedOrder;

    List<WarehouseRemovalProductResponse> warehouseRemovalProducts;

    Integer number;

    Long sendAmount;

    Long orderAmount;

    Long remainingAmount;
}
