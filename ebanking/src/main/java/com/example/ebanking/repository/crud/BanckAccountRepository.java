package com.example.ebanking.repository.crud;

import com.example.ebanking.entity.BankAccount;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BanckAccountRepository {

    private EntityManager entityManager;

    @Autowired
    public BanckAccountRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public BankAccount createAccount(BankAccount bankAccount) {
        try {
            entityManager.persist(bankAccount);
            return bankAccount;
        } catch (Exception e) {
            throw new RuntimeException("Error creating account: " + e.getMessage(), e);
        }
    }

    @Transactional
    public BankAccount updateAccount(BankAccount bankAccount) {
        try {
            return entityManager.merge(bankAccount);
        } catch (Exception e) {
            throw new RuntimeException("Error updating account: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteAccount(Long bankAccountId) {
        try {
            BankAccount bankAccount = entityManager.find(BankAccount.class, bankAccountId);
            entityManager.remove(bankAccount);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting account: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public BankAccount getAccountById(Long bankAccountId) {
        try {
            return entityManager.find(BankAccount.class, bankAccountId);
        } catch (Exception e) {
            throw new RuntimeException("Error getting account: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public BankAccount getAccountByAccountNumber(String accountNumber) {
        try {
            return entityManager.createQuery("SELECT b FROM BankAccount b WHERE b.accountNumber = :accountNumber", BankAccount.class)
                                .setParameter("accountNumber", accountNumber)
                                .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Error getting account: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public List<BankAccount> getAllAccounts() {
        try {
            return entityManager.createQuery("SELECT b FROM BankAccount b", BankAccount.class)
                                .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error getting accounts: " + e.getMessage(), e);
        }
    }
}
