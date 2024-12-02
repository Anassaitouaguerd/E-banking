package com.example.ebanking.service.crud;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.util.ObjectBuilder;
import com.example.ebanking.document.UserDocument;
import com.example.ebanking.entity.User;
import com.example.ebanking.mapper.users.UserDocumentMapper;
import com.example.ebanking.repository.elasticsearch.UserSearchRepository;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSearchService {
    private final UserSearchRepository userSearchRepository;
    private final ElasticsearchClient esClient;
    private final UserDocumentMapper userDocumentMapper;

    public List<UserDocument> searchUsers(String query) throws IOException {
        SearchResponse<UserDocument> response = esClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                .multiMatch(m -> m
                                        .query(query)
                                        .fields("username^2", "email^1.5", "firstName", "lastName")
                                )
                        ),
                UserDocument.class
        );

        return response.hits().hits().stream()
                .map(hit -> hit.source())
                .collect(Collectors.toList());
    }

    public void indexUser(User user) {
        // Index in Elasticsearch
        try {
            UserDocument userDocument = userDocumentMapper.toDocument(user);
            esClient.index(i -> i
                    .index("users")
                    .id(user.getId().toString())
                    .document(userDocument)
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to index user in Elasticsearch", e);
        }
    }

    public void deleteUser(String id) {
        // Delete from Elasticsearch
        try {
            esClient.delete(d -> d
                    .index("users")
                    .id(id)
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete user from Elasticsearch", e);
        }
    }

    // Alternative simpler approach using a string query
    public List<UserDocument> simpleSearchUsers(String query) {
        return userSearchRepository.findByUsernameContainingOrEmailContainingOrFirstNameContainingOrLastNameContaining(
                query, query, query, query
        );
    }
}