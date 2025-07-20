package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class TeethOperationNotFoundException extends RuntimeException {

    private final String message;
    public TeethOperationNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
