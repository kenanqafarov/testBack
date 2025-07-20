package com.rustam.modern_dentistry.dao.entity.settings.product;

import com.rustam.modern_dentistry.dao.entity.enums.status.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "product_name", nullable = false)
    String productName;

//    Long quantity;
//    BigDecimal price;
//
//    @Column(name = "sum_price")
//    BigDecimal sumPrice;

    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "product_no")
    Long productNo;

    @Column(name = "product_title")
    String productTitle;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;
}
