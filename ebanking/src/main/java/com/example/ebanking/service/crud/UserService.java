package com.example.ebanking.service.crud;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.ebanking.DTO.users.*;
import com.example.ebanking.document.UserDocument;
import com.example.ebanking.entity.User;
import com.example.ebanking.entity.enums.Role;
import com.example.ebanking.mapper.users.UserMapper;
import com.example.ebanking.repository.crud.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.ElasticsearchClient;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserSearchService userSearchService;
    private final ElasticsearchClient esClient;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("Email already exists");
                });

        User user = userMapper.toEntity(request);
        user.setPassword(request.getPassword());

        user = userRepository.save(userMapper.toEntity(request));
        userSearchService.indexUser(user);
        return userMapper.toResponseDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateDTO request) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            userRepository.findByEmail(request.getEmail())
                          .ifPresent(existingUser -> {
                            throw new RuntimeException("Email already exists");
                          });
        }

        userMapper.updateUserFromDTO(request, user);
        user = userRepository.update(user);
        userSearchService.indexUser(user);
        return userMapper.toResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.getAllUsers().stream()
                             .map(userMapper::toResponseDTO)
                             .collect(Collectors.toList());
    }

    @Transactional
    public void updatePassword(Long id, UserPasswordUpdateDTO request) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify current password
        if (request.getCurrentPassword().equals(user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Verify password confirmation
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("New password and confirmation do not match");
        }

        user.setPassword(request.getNewPassword());
        userRepository.update(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.findById(id)
                      .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(id);
        userSearchService.deleteUser(id.toString());
    }

    @Transactional(readOnly = true)
    public UserSummaryDTO getUserSummary(Long id) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toSummaryDTO(user);
    }

    public List<UserResponseDTO> searchUsers(String query) {
        try {
            SearchResponse<UserDocument> response = esClient.search(s -> s
                            .index("users")
                            .query(q -> q
                                    .multiMatch(m -> m
                                            .query(query)
                                            .fields("username^2", "email^1.5", "firstName", "lastName", "fullName")
                                    )
                            ),
                    UserDocument.class
            );

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .map(doc -> UserResponseDTO.builder()
                            .id(Long.parseLong(doc.getId()))
                            .username(doc.getUsername())
                            .email(doc.getEmail())
                            .firstName(doc.getFirstName())
                            .lastName(doc.getLastName())
                            .status(doc.isStatus())
                            .build())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<UserResponseDTO> advancedSearch(String query, String role, Boolean status,
                                                int size) {
        try {

            // Build the search query
            SearchResponse<UserDocument> response = esClient.search(s -> {
                // Start with the basic search builder
                var searchBuilder = s
                        .index("users")
                        .size(size);

                // Build the query based on parameters
                searchBuilder.query(q -> {
                    var boolQuery = q.bool(b -> {
                        // Add must clause for text search if query is not empty
                        if (query != null && !query.trim().isEmpty()) {
                            b.must(m -> m
                                    .multiMatch(mm -> mm
                                            .query(query)
                                            .fields("username^2", "email^1.5", "firstName", "lastName", "fullName" , "role")
                                    )
                            );
                        }

                        // Add filter for role if specified
                        if (role != null && !role.trim().isEmpty()) {
                            b.filter(f -> f
                                    .term(t -> t
                                            .field("role")
                                            .value(role)
                                    )
                            );
                        }

                        // Add filter for status if specified
                        if (status != null) {
                            b.filter(f -> f
                                    .term(t -> t
                                            .field("status")
                                            .value(status)
                                    )
                            );
                        }

                        return b;
                    });
                    return boolQuery;
                });

                return searchBuilder;
            }, UserDocument.class);

            // Transform and return results
            return response.hits().hits().stream()
                    .map(hit -> {
                        UserDocument doc = hit.source();
                        try {
                            return UserResponseDTO.builder()
                                    .id(Long.parseLong(doc.getId()))
                                    .username(doc.getUsername())
                                    .email(doc.getEmail())
                                    .firstName(doc.getFirstName())
                                    .lastName(doc.getLastName())
                                    .status(doc.isStatus())
                                    .role(doc.getRole() != null ? Role.valueOf(doc.getRole()) : null)
                                    .build();
                        } catch (IllegalArgumentException e) {
                            return UserResponseDTO.builder()
                                    .id(Long.parseLong(doc.getId()))
                                    .username(doc.getUsername())
                                    .email(doc.getEmail())
                                    .firstName(doc.getFirstName())
                                    .lastName(doc.getLastName())
                                    .status(doc.isStatus())
                                    .role(null)
                                    .build();
                        }
                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    @Transactional(readOnly = true)
    public void reindexAllUsers() {
        List<User> users = userRepository.getAllUsers();
        users.forEach(userSearchService::indexUser);
    }
}