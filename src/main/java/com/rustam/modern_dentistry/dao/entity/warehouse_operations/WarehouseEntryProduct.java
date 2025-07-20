package com.rustam.modern_dentistry.dao.entity.warehouse_operations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "warehouse_entry_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseEntryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "warehouse_entry_id")
    @JsonBackReference
    WarehouseEntry warehouseEntry;

    @Column(name = "category_id")
    Long categoryId;

    @Column(name = "category_name")
    String categoryName;

    @Column(name = "product_name")
    String productName;

    @Column(name = "product_title")
    String productTitle;

    @Column(name = "product_id")
    Long productId;

    Long quantity;

    @Column(name = "used_quantity")
    Long usedQuantity;

    BigDecimal price;
}
