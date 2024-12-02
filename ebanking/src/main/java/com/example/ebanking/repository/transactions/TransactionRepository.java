package com.example.ebanking.repository.transactions;

import com.example.ebanking.entity.Transaction;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {
    private EntityManager entityManager;

    @Transactional
    public void transfer(Transaction transaction) {
        try {
            entityManager.persist(transaction);
        } catch (Exception e) {
            throw new RuntimeException("Error creating transaction: " + e.getMessage(), e);
        }

    }
}
