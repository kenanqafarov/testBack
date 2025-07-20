package com.rustam.modern_dentistry.dto.response.read;

public interface WarehouseReadProjection {
    Long getId();
    String getCategoryName();
    String getProductName();
    Long getProductNo();
    Long getSumQuantity();
}
