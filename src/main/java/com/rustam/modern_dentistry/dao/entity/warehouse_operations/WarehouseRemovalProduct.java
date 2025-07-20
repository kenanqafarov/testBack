package com.rustam.modern_dentistry.dao.entity.warehouse_operations;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rustam.modern_dentistry.dao.entity.enums.status.PendingStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "warehouse_removal_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseRemovalProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate date;
    LocalTime time;
    @Column(name = "category_id")
    Long categoryId;
    @Column(name = "product_id")
    Long productId;
    @Column(name = "current_amount")
    Long currentAmount;
    @Column(name = "order_amount")
    Long orderAmount;
    @Column(name = "send_amount")
    Long sendAmount;
    @Column(name = "remaining_amount")
    Long remainingAmount;

    @Column(name = "product_name")
    String productName;
    @Column(name = "category_name")
    String categoryName;
    @Column(name = "product_description")
    String productDescription;

    @Enumerated(EnumType.STRING)
    PendingStatus pendingStatus;

    Integer number;

    @Column(name = "group_id")
    String groupId;

    @Column(name = "order_from_warehouse_product_id")
    Long orderFromWarehouseProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_removal_id")
    WarehouseRemoval warehouseRemoval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_receipts_id")
    WarehouseReceipts warehouseReceipts;


}
