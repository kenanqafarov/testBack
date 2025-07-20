package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class NoSuchPatientWasFound extends RuntimeException {
    private final String message;
    public NoSuchPatientWasFound(String message) {
        super(message);
        this.message = message;
    }
}
