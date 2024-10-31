package com.example.ebanking.service.crud;

import com.example.ebanking.entity.User;
import com.example.ebanking.repository.crud.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.update(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}
