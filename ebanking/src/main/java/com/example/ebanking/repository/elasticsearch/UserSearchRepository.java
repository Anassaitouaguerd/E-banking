package com.example.ebanking.repository.elasticsearch;

import com.example.ebanking.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserSearchRepository extends ElasticsearchRepository<UserDocument, String> {
    List<UserDocument> findByUsernameContainingOrEmailContainingOrFirstNameContainingOrLastNameContaining(
            String username, String email, String firstName, String lastName);
}