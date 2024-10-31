package com.example.ebanking.entity.enums;

import lombok.Getter;

@Getter
public enum TransferFrequency {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    QUARTERLY("Quarterly"),
    YEARLY("Yearly");

    private final String displayName;

    TransferFrequency(String displayName) {
        this.displayName = displayName;
    }

}