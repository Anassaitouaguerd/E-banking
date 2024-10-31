package com.example.ebanking.entity.enums;

import lombok.Getter;

@Getter
public enum LoanStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    ACTIVE("Active"),
    COMPLETED("Completed"),
    DEFAULTED("Defaulted");

    private final String displayName;

    LoanStatus(String displayName) {
        this.displayName = displayName;
    }

}