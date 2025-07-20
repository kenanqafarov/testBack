package com.rustam.modern_dentistry.dto.response;

public interface OrderProductStockProjection {
    String getCategoryName();
    String getProductName();
    String getProductCode();
    Double getEntryQuantity();
    Double getUsedQuantity();
    Double getRemainingQuantity();
}

