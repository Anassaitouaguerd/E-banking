package com.example.ebanking.controller.crud;

import com.example.ebanking.DTO.bankAccount.BankAccountRequestDTO;
import com.example.ebanking.DTO.bankAccount.BankAccountResponseDTO;
import com.example.ebanking.service.crud.BankAccountService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @PostMapping("/add")
    public ResponseEntity<BankAccountResponseDTO> createAccount(
            @Valid @RequestBody BankAccountRequestDTO request,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        BankAccountResponseDTO response = bankAccountService.createAccount(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountResponseDTO> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody BankAccountRequestDTO request) {
        BankAccountResponseDTO response = bankAccountService.updateAccount(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        bankAccountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponseDTO> getAccountById(@PathVariable Long id) {
        BankAccountResponseDTO response = bankAccountService.getAccountById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account-number/{accountNumber}")
    public ResponseEntity<BankAccountResponseDTO> getAccountByAccountNumber(
            @PathVariable String accountNumber) {
        BankAccountResponseDTO response = bankAccountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BankAccountResponseDTO>> getAllAccounts() {
        List<BankAccountResponseDTO> accounts = bankAccountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BankAccountResponseDTO>> getAccountsByUserId(
            @PathVariable Long userId) {
        List<BankAccountResponseDTO> accounts = bankAccountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/admin/changeStatus/{accountId}")
    public ResponseEntity<?> changeStatus(@RequestParam String status, @PathVariable Long accountId) {
        bankAccountService.changeStatus(accountId, Boolean.valueOf(String.valueOf(Objects.equals(status, "ACTIVE"))));
        return ResponseEntity.ok().build();
    }
}