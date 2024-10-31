package com.example.ebanking.entity;

import com.example.ebanking.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_fees")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    private boolean isInterbank;

    @NotNull(message = "Fee percentage is required")
    @DecimalMin(value = "0.0", message = "Fee percentage cannot be negative")
    @DecimalMax(value = "100.0", message = "Fee percentage cannot be greater than 100%")
    private BigDecimal feePercentage;

    @NotNull(message = "Minimum fee is required")
    @PositiveOrZero(message = "Minimum fee must be positive or zero")
    private BigDecimal minimumFee;

    @NotNull(message = "Maximum fee is required")
    @Positive(message = "Maximum fee must be positive")
    private BigDecimal maximumFee;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}