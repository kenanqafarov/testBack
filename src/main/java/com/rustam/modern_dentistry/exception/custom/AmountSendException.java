package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class AmountSendException extends RuntimeException {

    private final String message;

    public AmountSendException(String message) {
        super(message);
        this.message = message;
    }
}
