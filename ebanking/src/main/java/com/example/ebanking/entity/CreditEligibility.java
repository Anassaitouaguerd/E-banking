package com.example.ebanking.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_eligibility")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditEligibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @NotNull(message = "Monthly income is required")
    @Positive(message = "Monthly income must be positive")
    private BigDecimal monthlyIncome;

    @NotNull(message = "Debt ratio is required")
    @DecimalMin(value = "0.0", message = "Debt ratio cannot be negative")
    @DecimalMax(value = "1.0", message = "Debt ratio cannot be greater than 1")
    private BigDecimal debtRatio;

    @Range(min = 300, max = 850, message = "Credit score must be between 300 and 850")
    private Integer creditScore;

    @PositiveOrZero(message = "Bank relationship duration must be positive or zero")
    private Integer bankRelationshipDuration;

    private boolean hasGuarantee;

    @Size(max = 100)
    private String guaranteeType;

    @PositiveOrZero(message = "Guarantee value must be positive or zero")
    private BigDecimal guaranteeValue;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;
}