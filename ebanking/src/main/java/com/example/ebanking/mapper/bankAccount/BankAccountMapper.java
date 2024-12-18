package com.example.ebanking.mapper.bankAccount;

import com.example.ebanking.DTO.bankAccount.BankAccountRequestDTO;
import com.example.ebanking.DTO.bankAccount.BankAccountResponseDTO;
import com.example.ebanking.entity.BankAccount;
import com.example.ebanking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    @Mapping(target = "user.id", source = "userId")
    BankAccount toEntity(BankAccountRequestDTO dto);

    @Mapping(target = "userId", source = "user.id")
    BankAccountResponseDTO toDTO(BankAccount entity);

    @Named("userIdToUser")
    default User userIdToUser(Long userId) {
        if (userId == null) return null;
        return User.builder().id(userId).build();
    }
}
