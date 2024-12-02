package com.example.ebanking.controller.transactions.users;

import com.example.ebanking.DTO.transactions.users.TransactionsRepDTO;
import com.example.ebanking.DTO.transactions.users.TransactionsReqDTO;
import com.example.ebanking.service.Transactions.TransactionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionsService transactionsService;

    @PostMapping("/transfer")
    public void transfer(@Valid @RequestBody TransactionsReqDTO request) {
        TransactionsRepDTO transfer = transactionsService.transfer(request);
    }

}
