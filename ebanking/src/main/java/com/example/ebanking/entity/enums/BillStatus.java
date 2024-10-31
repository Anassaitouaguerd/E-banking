package com.example.ebanking.entity.enums;

import lombok.Getter;

@Getter
public enum BillStatus {
    PAID("Paid"),
    UNPAID("Unpaid"),
    OVERDUE("Overdue"),
    CANCELLED("Cancelled"),
    SCHEDULED("Scheduled");

    private final String displayName;

    BillStatus(String displayName) {
        this.displayName = displayName;
    }

}
