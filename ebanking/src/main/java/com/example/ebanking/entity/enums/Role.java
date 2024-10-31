package com.example.ebanking.entity.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("Administrator"),
    USER("Regular User"),
    EMPLOYEE("Bank Employee");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

}