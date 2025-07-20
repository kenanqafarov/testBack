package com.rustam.modern_dentistry.exception.custom;

import lombok.Getter;

@Getter
public class DoctorIsPatientsWereNotFound extends RuntimeException {
    private final String message;
    public DoctorIsPatientsWereNotFound(String message) {
        super(message);
        this.message = message;
    }
}
