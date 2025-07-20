package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class CustomError extends RuntimeException {
    private final String message;

    public CustomError(String message) {
        super(message);
        this.message = message;
    }
}
