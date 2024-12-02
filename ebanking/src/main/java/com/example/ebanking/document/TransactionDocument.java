package com.example.ebanking.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(indexName = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDocument {
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Double)
    private BigDecimal amount;

    @Field(type = FieldType.Keyword)
    private String transactionType;

    @Field(type = FieldType.Keyword)
    private String sourceAccountId;

    @Field(type = FieldType.Keyword)
    private String sourceAccountOwnerName;

    @Field(type = FieldType.Keyword)
    private String destinationAccountId;

    @Field(type = FieldType.Keyword)
    private String destinationAccountOwnerName;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Double)
    private BigDecimal transactionFee;

    @Field(type = FieldType.Keyword)
    private String approvedBy;

    @Field(type = FieldType.Boolean)
    private boolean isInterbank;
}