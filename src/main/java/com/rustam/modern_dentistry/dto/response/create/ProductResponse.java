package com.rustam.modern_dentistry.dto.response.create;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {

    Long id;
    Long categoryId;
    String categoryName;
    String productName;
    Long productNo;
    String productTitle;
}
