package com.proyect.eva.service;

import com.proyect.eva.dto.PhoneDto;
import com.proyect.eva.dto.UserRequestDto;
import com.proyect.eva.dto.UserResponseDto;
import com.proyect.eva.entity.User;
import com.proyect.eva.exception.EmailAlreadyExistsException;
import com.proyect.eva.exception.UserNotFoundException;
import com.proyect.eva.mapper.UserMapper;
import com.proyect.eva.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private JwtService jwtService;
    
    @Mock
    private ValidationService validationService;
    
    @Mock
    private UserMapper userMapper;
    
    @InjectMocks
    private UserService userService;
    
    private UserRequestDto userRequest;
    private User user;
    private UserResponseDto userResponse;
    
    @BeforeEach
    void setUp() {
        PhoneDto phone = new PhoneDto("1234567", "1", "57");
        userRequest = new UserRequestDto("Juan Rodriguez", "juan@rodriguez.org", "hunter2", List.of(phone));
        
        user = new User("Juan Rodriguez", "juan@rodriguez.org", "hunter2", "token123");
        user.setId(UUID.randomUUID());
        
        userResponse = new UserResponseDto();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
    }
    
    @Test
    void createUser_Success() {
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(jwtService.generateToken(userRequest.getEmail())).thenReturn("token123");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(userResponse);
        
        UserResponseDto result = userService.createUser(userRequest);
        
        assertNotNull(result);
        assertEquals(userRequest.getName(), result.getName());
        assertEquals(userRequest.getEmail(), result.getEmail());
        verify(validationService).validateEmail(userRequest.getEmail());
        verify(validationService).validatePassword(userRequest.getPassword());
    }
    
    @Test
    void createUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(true);
        
        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(userRequest));
    }
    
    @Test
    void getUserById_Success() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(userResponse);
        
        UserResponseDto result = userService.getUserById(userId);
        
        assertNotNull(result);
        assertEquals(userResponse.getName(), result.getName());
    }
    
    @Test
    void getUserById_NotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
    }
    
    @Test
    void deleteUser_Success() {
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(true);
        
        assertDoesNotThrow(() -> userService.deleteUser(userId));
        verify(userRepository).deleteById(userId);
    }
    
    @Test
    void deleteUser_NotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.existsById(userId)).thenReturn(false);
        
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
    }
}