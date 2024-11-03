package com.example.ebanking.mapper.users;

import com.example.ebanking.DTO.users.*;
import com.example.ebanking.entity.User;
import com.example.ebanking.entity.enums.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "status", constant = "true")
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponseDTO(User user);

    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    UserSummaryDTO toSummaryDTO(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);

    @AfterMapping
    default void setDefaults(@MappingTarget User user) {
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
    }
}