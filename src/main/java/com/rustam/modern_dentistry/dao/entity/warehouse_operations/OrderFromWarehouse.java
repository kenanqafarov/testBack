package com.rustam.modern_dentistry.dao.entity.warehouse_operations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_from_warehouse")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderFromWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate date;

    LocalTime time;

    @Enumerated(EnumType.STRING)
    Room room;

    @OneToMany(mappedBy = "orderFromWarehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<OrderFromWarehouseProduct> orderFromWarehouseProducts;

    String description;

    @Column(name = "person_who_placed_order")
    String personWhoPlacedOrder;

    Integer number;

    @Column(name = "sum_quantity")
    Long sumQuantity;

    @OneToOne(mappedBy = "orderFromWarehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    WarehouseRemoval warehouseRemoval;

}
