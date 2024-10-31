package com.example.ebanking.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
@Table(name = "bank_relationships")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "Relationship status is required")
    @Size(max = 50)
    private String relationshipStatus;

    @UpdateTimestamp
    private LocalDate lastReviewDate;

    @Range(min = 300, max = 850, message = "Credit score must be between 300 and 850")
    private Integer creditScore;
}