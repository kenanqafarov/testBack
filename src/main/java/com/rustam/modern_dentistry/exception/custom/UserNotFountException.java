package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class UserNotFountException extends RuntimeException {
    private final String message;
    public UserNotFountException(String message) {
        super(message);
        this.message = message;
    }
}
