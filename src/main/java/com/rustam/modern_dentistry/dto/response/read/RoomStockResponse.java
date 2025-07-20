package com.rustam.modern_dentistry.dto.response.read;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomStockResponse {

     String categoryName;
     String productName;
     String productCode;
     Double entryQuantity;
     Double usedQuantity;
     Double remainingQuantity;
}
