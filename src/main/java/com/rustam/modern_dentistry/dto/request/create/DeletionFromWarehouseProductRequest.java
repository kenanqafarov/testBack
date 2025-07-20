package com.rustam.modern_dentistry.dto.request.create;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletionFromWarehouseProductRequest {
    Long deletionFromWarehouseProductId;
    Long warehouseEntryId;
    Long warehouseEntryProductId;
    Long productId;
    Long categoryId;
    Long quantity;
}
