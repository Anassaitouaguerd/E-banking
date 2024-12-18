package com.example.ebanking.service.crud;

import com.example.ebanking.DTO.bankAccount.BankAccountRequestDTO;
import com.example.ebanking.DTO.bankAccount.BankAccountResponseDTO;
import com.example.ebanking.entity.BankAccount;
import com.example.ebanking.mapper.bankAccount.BankAccountMapper;
import com.example.ebanking.repository.crud.BanckAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BanckAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    @Transactional
    public BankAccountResponseDTO createAccount(BankAccountRequestDTO request, Long userId) {
        BankAccount bankAccount = bankAccountMapper.toEntity(request);
        bankAccount.setUserId(userId);
        BankAccount savedAccount = bankAccountRepository.createAccount(bankAccount);
        return bankAccountMapper.toDTO(savedAccount);
    }

    @Transactional
    public BankAccountResponseDTO updateAccount(Long id, BankAccountRequestDTO request) {
        BankAccount existingAccount = bankAccountRepository.getAccountById(id);
        if (existingAccount == null) {
            throw new RuntimeException("Account not found");
        }

        BankAccount updatedAccount = bankAccountMapper.toEntity(request);
        updatedAccount.setId(id);
        updatedAccount.setUserId(existingAccount.getUser().getId());

        BankAccount savedAccount = bankAccountRepository.updateAccount(updatedAccount);
        return bankAccountMapper.toDTO(savedAccount);
    }

    @Transactional
    public void deleteAccount(Long bankAccountId) {
        BankAccount account = bankAccountRepository.getAccountById(bankAccountId);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        bankAccountRepository.deleteAccount(bankAccountId);
    }

    @Transactional(readOnly = true)
    public BankAccountResponseDTO getAccountById(Long bankAccountId) {
        BankAccount account = bankAccountRepository.getAccountById(bankAccountId);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        return bankAccountMapper.toDTO(account);
    }

    @Transactional(readOnly = true)
    public BankAccountResponseDTO getAccountByAccountNumber(String accountNumber) {
        BankAccount account = bankAccountRepository.getAccountByAccountNumber(accountNumber);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        return bankAccountMapper.toDTO(account);
    }

    @Transactional(readOnly = true)
    public List<BankAccountResponseDTO> getAllAccounts() {
        return bankAccountRepository.getAllAccounts().stream()
                .map(bankAccountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BankAccountResponseDTO> getAccountsByUserId(Long userId) {
        return bankAccountRepository.getAllAccounts().stream()
                .filter(account -> account.getUser().getId().equals(userId))
                .map(bankAccountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeStatus(Long accountId, Boolean status) {
        BankAccount account = bankAccountRepository.getAccountById(accountId);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        account.setStatus(status);
        bankAccountRepository.updateAccount(account);
    }
}