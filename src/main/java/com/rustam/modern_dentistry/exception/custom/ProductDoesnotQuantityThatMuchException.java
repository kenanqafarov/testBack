package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class ProductDoesnotQuantityThatMuchException extends RuntimeException {

    private final String message;
    public ProductDoesnotQuantityThatMuchException(String message) {
        super(message);
        this.message = message;
    }
}
