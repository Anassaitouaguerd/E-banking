package com.example.ebanking;

import com.example.ebanking.DTO.users.UserRequestDTO;
import com.example.ebanking.DTO.users.UserResponseDTO;
import com.example.ebanking.DTO.users.UserUpdateDTO;
import com.example.ebanking.entity.User;
import com.example.ebanking.mapper.users.UserMapper;
import com.example.ebanking.repository.crud.UserRepository;
import com.example.ebanking.service.crud.UserSearchService;
import com.example.ebanking.service.crud.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserSearchService userSearchService;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO userRequestDTO;
    private UserUpdateDTO userUpdateDTO;
    private User user;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        userRequestDTO = UserRequestDTO.builder()
                .email("test@example.com")
                .password("password123")
                .username("testuser")
                .firstName("Test")
                .lastName("User")
                .build();

        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("password123")
                .username("testuser")
                .firstName("Test")
                .lastName("User")
                .build();

        userResponseDTO = UserResponseDTO.builder()
                .id(1L)
                .email("test@example.com")
                .username("testuser")
                .firstName("Test")
                .lastName("User")
                .build();

        userUpdateDTO = UserUpdateDTO.builder()
                .email("updated@example.com")
                .username("updateduser")
                .firstName("Updated")
                .lastName("User")
                .build();
    }

    @Test
    void createUser_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.toEntity(any(UserRequestDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.createUser(userRequestDTO);

        assertNotNull(result);
        assertEquals(userResponseDTO.getId(), result.getId());
        assertEquals(userResponseDTO.getEmail(), result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_EmailExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> userService.createUser(userRequestDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.update(any(User.class))).thenReturn(user);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.updateUser(1L, userUpdateDTO);

        assertNotNull(result);
        verify(userMapper).updateUserFromDTO(any(UserUpdateDTO.class), any(User.class));
        verify(userRepository).update(any(User.class));
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(userResponseDTO.getId(), result.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getAllUsers_Success() {
        List<User> users = Arrays.asList(user);
        when(userRepository.getAllUsers()).thenReturn(users);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(userResponseDTO);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository).getAllUsers();
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(anyLong());

        userService.deleteUser(1L);

        verify(userRepository).delete(1L);
    }
}