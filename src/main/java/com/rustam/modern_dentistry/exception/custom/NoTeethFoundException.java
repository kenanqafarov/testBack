package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class NoTeethFoundException extends RuntimeException {

    private final String message;
    public NoTeethFoundException(String message) {
        super(message);
        this.message = message;
    }
}
