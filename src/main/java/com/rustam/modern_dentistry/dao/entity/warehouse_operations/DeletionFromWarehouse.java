package com.rustam.modern_dentistry.dao.entity.warehouse_operations;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "deletion_from_warehouse")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletionFromWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate date;

    LocalTime time;

    String description;

    @OneToMany(mappedBy = "deletionFromWarehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DeletionFromWarehouseProduct> deletionFromWarehouseProducts;

    Integer number;
}

