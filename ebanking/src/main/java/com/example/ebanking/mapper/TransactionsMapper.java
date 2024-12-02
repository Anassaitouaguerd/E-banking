//package com.example.ebanking.mapper;
//
//import com.example.ebanking.DTO.transactions.users.TransactionsRepDTO;
//import com.example.ebanking.DTO.transactions.users.TransactionsReqDTO;
//import com.example.ebanking.entity.Transaction;
//import org.mapstruct.*;
//
//@Mapper(componentModel = "spring")
//public interface TransactionsMapper {
//    @Mapping(target = "id", ignore = true)
//    Transaction toEntity(TransactionsReqDTO dto);
//    TransactionsRepDTO toResponseDTO(Transaction transaction);
//}
