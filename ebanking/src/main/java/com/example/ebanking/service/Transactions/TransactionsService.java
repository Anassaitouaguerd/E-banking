//package com.example.ebanking.service.Transactions;
//
//import com.example.ebanking.DTO.transactions.users.TransactionsRepDTO;
//import com.example.ebanking.DTO.transactions.users.TransactionsReqDTO;
//import com.example.ebanking.document.TransactionDocument;
//import com.example.ebanking.entity.BankAccount;
//import com.example.ebanking.entity.Transaction;
//import com.example.ebanking.entity.User;
//import com.example.ebanking.entity.enums.TransactionStatus;
//import com.example.ebanking.entity.enums.TransactionType;
//import com.example.ebanking.mapper.TransactionsMapper;
//import com.example.ebanking.repository.crud.BanckAccountRepository;
//import com.example.ebanking.repository.elasticsearch.TransactionElasticsearchRepository;
//import com.example.ebanking.repository.transactions.TransactionRepository;
//import com.example.ebanking.service.crud.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.security.auth.login.AccountNotFoundException;
//import java.math.BigDecimal;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class TransactionsService {
//    private final TransactionRepository transactionRepository;
//    private final TransactionElasticsearchRepository elasticsearchRepository;
//    private final BanckAccountRepository bankAccountRepository;
//    private final TransactionsMapper transactionsMapper;
////    private final TransactionFeeService feeService;
//    private final UserService userService;
//
//    @Transactional
//    public TransactionsRepDTO transfer(TransactionsReqDTO request) {
//        User currentUser = userService.getCurrentUser();
//        validateTransfer(request, currentUser);
//
//        BankAccount sourceAccount = bankAccountRepository.getBankAccountByUserId(currentUser.getId())
//                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
//        BankAccount destinationAccount = bankAccountRepository.getAccountByAccountNumber(
//                        String.valueOf(request.getDestination_account_id()))
//                .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));
//
//        BigDecimal amount = new BigDecimal(request.getAmount());
//        TransactionType transactionType = determineTransactionType(sourceAccount, destinationAccount);
//        TransactionStatus status = determineInitialStatus(amount, transactionType);
//        BigDecimal fee = feeService.calculateFee(amount, transactionType, request.getIs_interbank());
//
//        // Check balance including fee
//        validateBalance(sourceAccount, amount, fee);
//
//        // Create and save transaction
//        Transaction transaction = createTransaction(request, sourceAccount, destinationAccount,
//                transactionType, status, fee);
//
//        // Process the transaction based on status
//        processTransaction(transaction, sourceAccount, destinationAccount);
//
//        // Index to ElasticSearch
//        indexTransactionToElasticsearch(transaction);
//
//        return transactionsMapper.toResponseDTO(transaction);
//    }
////
////    @Transactional
////    public TransactionsRepDTO createRecurringTransfer(RecurringTransferReqDTO request) {
////        // Implementation for creating recurring transfers
////        // Similar to transfer but with scheduling logic
////    }
////
////    public List<TransactionsRepDTO> searchTransactions(TransactionSearchCriteria criteria) {
////        // Implementation for searching transactions using ElasticSearch
////    }
//
//    private void validateTransfer(TransactionsReqDTO request, User user) {
//        // Implement validation logic
//    }
//
//    private TransactionType determineTransactionType(BankAccount source, BankAccount destination) {
//        // Implement logic to determine transaction type
//    }
//
//    private TransactionStatus determineInitialStatus(BigDecimal amount, TransactionType type) {
//        // Implement logic to determine if transaction needs approval
//    }
//
//    private void validateBalance(BankAccount account, BigDecimal amount, BigDecimal fee) {
//        // Implement balance validation
//    }
//
//    private void processTransaction(Transaction transaction, BankAccount source, BankAccount destination) {
//        // Implement transaction processing logic
//    }
//
//    private void indexTransactionToElasticsearch(Transaction transaction) {
//        TransactionDocument document = createElasticsearchDocument(transaction);
//        elasticsearchRepository.save(document);
//    }
//
//    private TransactionDocument createElasticsearchDocument(Transaction transaction) {
//        // Implement document creation logic
//    }
//}