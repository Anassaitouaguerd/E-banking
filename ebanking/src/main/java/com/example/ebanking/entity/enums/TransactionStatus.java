package com.example.ebanking.entity.enums;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    CANCELLED("Cancelled"),
    FAILED("Failed"),
    COMPLETED("Completed");

    private final String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

}
