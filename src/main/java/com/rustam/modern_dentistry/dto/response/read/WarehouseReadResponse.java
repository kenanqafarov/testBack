package com.rustam.modern_dentistry.dto.response.read;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseReadResponse {

    Long id;
    String categoryName;
    String productName;
    Long productNo;
    Long sumQuantity;
}
