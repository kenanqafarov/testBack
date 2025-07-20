package com.rustam.modern_dentistry.dao.entity.warehouse_operations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "warehouse_entry")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate date;

    LocalTime time;

    @OneToMany(mappedBy = "warehouseEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<WarehouseEntryProduct> warehouseEntryProducts;

    String description;

    Integer number;

    @Column(name = "sum_price")
    BigDecimal sumPrice;
}
