package com.example.ebanking.mapper.users;

import com.example.ebanking.DTO.auth.LoginResponseDTO;
import com.example.ebanking.DTO.auth.RegisterRequestDTO;
import com.example.ebanking.DTO.users.UserRequestDTO;
import com.example.ebanking.DTO.users.UserResponseDTO;
import com.example.ebanking.DTO.users.UserSummaryDTO;
import com.example.ebanking.DTO.users.UserUpdateDTO;
import com.example.ebanking.entity.User;
import com.example.ebanking.entity.enums.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-03T16:14:19+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( dto.getUsername() );
        user.password( dto.getPassword() );
        user.email( dto.getEmail() );
        user.firstName( dto.getFirstName() );
        user.lastName( dto.getLastName() );
        user.dateOfBirth( dto.getDateOfBirth() );

        user.role( Role.USER );
        user.status( true );

        return user.build();
    }

    @Override
    public User toEntity(RegisterRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( dto.getUsername() );
        user.password( dto.getPassword() );
        user.email( dto.getEmail() );
        user.firstName( dto.getFirstName() );
        user.lastName( dto.getLastName() );
        user.dateOfBirth( dto.getDateOfBirth() );

        return user.build();
    }

    @Override
    public LoginResponseDTO toLoginResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }

        LoginResponseDTO.LoginResponseDTOBuilder loginResponseDTO = LoginResponseDTO.builder();

        loginResponseDTO.id( user.getId() );
        loginResponseDTO.username( user.getUsername() );
        loginResponseDTO.email( user.getEmail() );
        loginResponseDTO.firstName( user.getFirstName() );
        loginResponseDTO.lastName( user.getLastName() );
        loginResponseDTO.role( user.getRole() );

        return loginResponseDTO.build();
    }

    @Override
    public UserResponseDTO toResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO.UserResponseDTOBuilder userResponseDTO = UserResponseDTO.builder();

        userResponseDTO.id( user.getId() );
        userResponseDTO.username( user.getUsername() );
        userResponseDTO.email( user.getEmail() );
        userResponseDTO.firstName( user.getFirstName() );
        userResponseDTO.lastName( user.getLastName() );
        userResponseDTO.dateOfBirth( user.getDateOfBirth() );
        userResponseDTO.role( user.getRole() );
        userResponseDTO.status( user.isStatus() );

        return userResponseDTO.build();
    }

    @Override
    public UserSummaryDTO toSummaryDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserSummaryDTO.UserSummaryDTOBuilder userSummaryDTO = UserSummaryDTO.builder();

        userSummaryDTO.id( user.getId() );
        userSummaryDTO.username( user.getUsername() );
        userSummaryDTO.email( user.getEmail() );

        userSummaryDTO.fullName( user.getFirstName() + " " + user.getLastName() );

        return userSummaryDTO.build();
    }

    @Override
    public void updateUserFromDTO(UserUpdateDTO dto, User user) {
        if ( dto == null ) {
            return;
        }

        user.setUsername( dto.getUsername() );
        user.setEmail( dto.getEmail() );
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );

        setDefaults( user );
    }
}
