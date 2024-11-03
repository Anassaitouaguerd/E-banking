package com.example.ebanking.mapper.bankAccount;

import com.example.ebanking.DTO.bankAccount.BankAccountRequestDTO;
import com.example.ebanking.DTO.bankAccount.BankAccountResponseDTO;
import com.example.ebanking.entity.BankAccount;
import com.example.ebanking.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-03T16:14:19+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class BankAccountMapperImpl implements BankAccountMapper {

    @Override
    public BankAccount toEntity(BankAccountRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BankAccount.BankAccountBuilder bankAccount = BankAccount.builder();

        bankAccount.user( bankAccountRequestDTOToUser( dto ) );
        bankAccount.accountNumber( dto.getAccountNumber() );
        bankAccount.balance( dto.getBalance() );
        bankAccount.accountType( dto.getAccountType() );
        bankAccount.currency( dto.getCurrency() );

        return bankAccount.build();
    }

    @Override
    public BankAccountResponseDTO toDTO(BankAccount entity) {
        if ( entity == null ) {
            return null;
        }

        BankAccountResponseDTO.BankAccountResponseDTOBuilder bankAccountResponseDTO = BankAccountResponseDTO.builder();

        bankAccountResponseDTO.userId( entityUserId( entity ) );
        bankAccountResponseDTO.id( entity.getId() );
        bankAccountResponseDTO.accountNumber( entity.getAccountNumber() );
        bankAccountResponseDTO.balance( entity.getBalance() );
        bankAccountResponseDTO.accountType( entity.getAccountType() );
        bankAccountResponseDTO.status( entity.isStatus() );
        bankAccountResponseDTO.currency( entity.getCurrency() );
        bankAccountResponseDTO.createdAt( entity.getCreatedAt() );

        return bankAccountResponseDTO.build();
    }

    protected User bankAccountRequestDTOToUser(BankAccountRequestDTO bankAccountRequestDTO) {
        if ( bankAccountRequestDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( bankAccountRequestDTO.getUserId() );

        return user.build();
    }

    private Long entityUserId(BankAccount bankAccount) {
        if ( bankAccount == null ) {
            return null;
        }
        User user = bankAccount.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
