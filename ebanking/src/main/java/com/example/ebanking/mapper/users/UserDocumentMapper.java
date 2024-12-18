package com.example.ebanking.mapper.users;


import com.example.ebanking.document.UserDocument;
import com.example.ebanking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDocumentMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", expression = "java(user.getFirstName() + ' ' + user.getLastName())")
    @Mapping(target = "role", expression = "java(user.getRole() != null ? user.getRole().name() : null)")
    UserDocument toDocument(User user);
}
