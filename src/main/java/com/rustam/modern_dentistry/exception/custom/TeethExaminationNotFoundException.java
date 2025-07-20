package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class TeethExaminationNotFoundException extends RuntimeException {
    private final String message;
    public TeethExaminationNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
