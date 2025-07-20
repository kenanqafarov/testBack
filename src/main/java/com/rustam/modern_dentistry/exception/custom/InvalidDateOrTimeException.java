package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class InvalidDateOrTimeException extends RuntimeException {
    private final String message;

    public InvalidDateOrTimeException(String message) {
        super(message);
        this.message = message;
    }
}
