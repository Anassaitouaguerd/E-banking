package com.example.ebanking.entity.enums;

import lombok.Getter;

@Getter
public enum LoanType {
    PERSONAL("Personal Loan"),
    MORTGAGE("Mortgage Loan"),
    BUSINESS("Business Loan"),
    AUTO("Auto Loan"),
    STUDENT("Student Loan"),
    HOME_EQUITY("Home Equity Loan");

    private final String displayName;

    LoanType(String displayName) {
        this.displayName = displayName;
    }

}
