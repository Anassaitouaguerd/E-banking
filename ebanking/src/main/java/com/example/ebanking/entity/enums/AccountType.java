package com.example.ebanking.entity.enums;


import lombok.Getter;

@Getter
public enum AccountType {
    SAVINGS("Savings Account"),
    CHECKING("Checking Account"),
    INVESTMENT("Investment Account");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

}