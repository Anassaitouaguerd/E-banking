package com.example.ebanking.service.crud;

import com.example.ebanking.DTO.users.*;
import com.example.ebanking.entity.User;
import com.example.ebanking.mapper.users.UserMapper;
import com.example.ebanking.repository.crud.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("Email already exists");
                });

        User user = userMapper.toEntity(request);
        user.setPassword(request.getPassword());

        user = userRepository.save(user);
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
    }


    @Transactional(readOnly = true)
    public UserSummaryDTO getUserSummary(Long id) {
        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toSummaryDTO(user);
    }
}