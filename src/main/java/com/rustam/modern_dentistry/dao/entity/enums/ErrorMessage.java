package com.rustam.modern_dentistry.dao.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    ERROR_MESSAGE("Error mesajlarınə bura daxil et.");

    private final String message;
    public String format(Object... args) {
        return String.format(message, args);
    }
}
