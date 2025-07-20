package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class WarehouseEntryNotFoundException extends RuntimeException {

    private final String message;

    public WarehouseEntryNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
