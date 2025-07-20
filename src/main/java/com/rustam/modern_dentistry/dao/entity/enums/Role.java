package com.rustam.modern_dentistry.dao.entity.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role {

    ADMIN("ADMIN"),
    DOCTOR_FULL_PERMISSION("DOCTOR_FULL_PERMISSION"),
    DOCTOR("DOCTOR"),
    RECEPTION("RECEPTION"),
    //PATIENT("PATIENT"),
    WAREHOUSE_MAN("WAREHOUSE_MAN"),
    USER("USER"),
    PATIENT("PATIENT"),
    ACCOUNTANT("ACCOUNTANT"),
    TECHNICIAN("TECHNICIAN"),

    SUPER_ADMIN("SUPER_ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }

}
