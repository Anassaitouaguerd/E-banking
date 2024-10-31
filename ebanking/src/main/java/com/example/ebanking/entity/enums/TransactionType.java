package com.example.ebanking.entity.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    CLASSIC("Classic Transfer"),
    INSTANT("Instant Transfer"),
    PERMANENT("Standing Order");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

}