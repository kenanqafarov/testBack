package com.rustam.modern_dentistry.dao.entity.warehouse_operations;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "deletion_from_warehouse_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletionFromWarehouseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deletion_from_warehouse_id")
    DeletionFromWarehouse deletionFromWarehouse;

    @Column(name = "warehouse_entry_product_id")
    Long warehouseEntryProductId;

    @Column(name = "warehouse_entry_id")
    Long warehouseEntryId;

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

    BigDecimal price;

    @Column(name = "used_quantity")
    Long usedQuantity;
}

