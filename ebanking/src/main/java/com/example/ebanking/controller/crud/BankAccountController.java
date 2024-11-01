package com.example.ebanking.controller.crud;

import com.example.ebanking.entity.BankAccount;
import com.example.ebanking.repository.crud.BanckAccountRepository;
import com.example.ebanking.service.crud.BankAccountService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class BankAccountController {

    private BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/api/account/add")
    public void createAccount(@RequestBody BankAccount account , HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        account.setUserId(userId);
        bankAccountService.createAccount(account);
    }

    @PutMapping("/api/account/update")
    public void updateAccount(@RequestBody BankAccount account) {
        bankAccountService.updateAccount(account);

    }

    @GetMapping("/api/account/delete")
    public void deleteAccount() {
        // TODO implement here
    }

    @GetMapping("/api/account/{id}")
    public void getAccountById(@PathVariable String id) {
        // TODO implement here
    }

    @GetMapping("/api/account/account-number/{accountNumber}")
    public void getAccountByAccountNumber(@PathVariable String accountNumber) {
        // TODO implement here
    }

    @GetMapping("/api/account/all")
    public void getAllAccounts() {
        // TODO implement here
    }
}
