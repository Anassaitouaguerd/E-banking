package com.example.ebanking.repository.elasticsearch;

import com.example.ebanking.document.TransactionDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionElasticsearchRepository extends ElasticsearchRepository<TransactionDocument, String> {
    List<TransactionDocument> findBySourceAccountId(String accountId);
    List<TransactionDocument> findByDestinationAccountId(String accountId);
    List<TransactionDocument> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
    List<TransactionDocument> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<TransactionDocument> findByTransactionType(String transactionType);
    List<TransactionDocument> findByStatus(String status);
    List<TransactionDocument> findByIsInterbank(boolean isInterbank);
}