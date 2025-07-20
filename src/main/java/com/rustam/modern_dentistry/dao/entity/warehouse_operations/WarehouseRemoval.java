package com.rustam.modern_dentistry.dao.entity.warehouse_operations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rustam.modern_dentistry.dao.entity.enums.status.Room;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "warehouse_removal")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseRemoval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate date;

    LocalTime time;

    @Enumerated(EnumType.STRING)
    Room room;

    @Column(name = "person_who_placed_order")
    String personWhoPlacedOrder;

    @OneToMany(mappedBy = "warehouseRemoval", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<WarehouseRemovalProduct> warehouseRemovalProducts;

    @OneToOne
    @JoinColumn(name = "order_from_warehouse_id")
    OrderFromWarehouse orderFromWarehouse;

    Integer number;

    @Column(name = "send_amount")
    Long sendAmount;

    @Column(name = "order_amount")
    Long orderAmount;

    @Column(name = "remaining_amount")
    Long remainingAmount;

}
