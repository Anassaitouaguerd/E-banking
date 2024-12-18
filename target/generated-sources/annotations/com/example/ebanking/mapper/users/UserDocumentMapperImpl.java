package com.example.ebanking.mapper.users;

import com.example.ebanking.document.UserDocument;
import com.example.ebanking.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-17T10:54:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class UserDocumentMapperImpl implements UserDocumentMapper {

    @Override
    public UserDocument toDocument(User user) {
        if ( user == null ) {
            return null;
        }

        UserDocument.UserDocumentBuilder userDocument = UserDocument.builder();

        if ( user.getId() != null ) {
            userDocument.id( String.valueOf( user.getId() ) );
        }
        userDocument.username( user.getUsername() );
        userDocument.email( user.getEmail() );
        userDocument.firstName( user.getFirstName() );
        userDocument.lastName( user.getLastName() );
        userDocument.status( user.isStatus() );

        userDocument.fullName( user.getFirstName() + ' ' + user.getLastName() );
        userDocument.role( user.getRole() != null ? user.getRole().name() : null );

        return userDocument.build();
    }
}
