package com.example.ebanking.service.crud;

import com.example.ebanking.entity.BankAccount;
import com.example.ebanking.repository.crud.BanckAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {
    private BanckAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BanckAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createAccount(BankAccount bankAccount) {
        return bankAccountRepository.createAccount(bankAccount);
    }

    public BankAccount updateAccount(BankAccount bankAccount) {
        return bankAccountRepository.updateAccount(bankAccount);
    }

    public void deleteAccount(Long bankAccountId) {
        bankAccountRepository.deleteAccount(bankAccountId);
    }

    public BankAccount getAccountById(Long bankAccountId) {
        return bankAccountRepository.getAccountById(bankAccountId);
    }

    public BankAccount getAccountByAccountNumber(String accountNumber) {
        return bankAccountRepository.getAccountByAccountNumber(accountNumber);
    }

    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.getAllAccounts();
    }
}
