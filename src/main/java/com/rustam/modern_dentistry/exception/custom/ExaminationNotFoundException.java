package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class ExaminationNotFoundException extends RuntimeException {

    private final String message;

    public ExaminationNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
