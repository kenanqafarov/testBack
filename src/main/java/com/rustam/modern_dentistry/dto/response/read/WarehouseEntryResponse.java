package com.rustam.modern_dentistry.dto.response.read;

import com.rustam.modern_dentistry.dao.entity.warehouse_operations.WarehouseEntryProduct;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseEntryResponse {

    LocalDate date;

    LocalTime time;

    List<WarehouseEntryProduct> warehouseEntryProducts;

    Integer number;

    String description;

    BigDecimal sumPrice;
}
