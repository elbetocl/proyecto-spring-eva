package com.proyect.eva.service;

import com.proyect.eva.dto.UserRequestDto;
import com.proyect.eva.dto.UserResponseDto;
import com.proyect.eva.entity.Phone;
import com.proyect.eva.entity.User;
import com.proyect.eva.exception.EmailAlreadyExistsException;
import com.proyect.eva.exception.UserNotFoundException;
import com.proyect.eva.mapper.UserMapper;
import com.proyect.eva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private ValidationService validationService;
    
    @Autowired
    private UserMapper userMapper;
    
    public UserResponseDto createUser(UserRequestDto request) {
        validationService.validateEmail(request.getEmail());
        validationService.validatePassword(request.getPassword());
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("El correo ya registrado");
        }
        
        String token = jwtService.generateToken(request.getEmail());
        
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .token(token)
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isactive(true)
                .build();
        
        List<Phone> phones = request.getPhones().stream()
                .<Phone>map(phoneDto -> Phone.builder()
                        .number(phoneDto.getNumber())
                        .citycode(phoneDto.getCitycode())
                        .contrycode(phoneDto.getContrycode())
                        .user(user)
                        .build())
                .toList();
        
        user.setPhones(phones);
        User savedUser = userRepository.save(user);
        
        return userMapper.toResponseDto(savedUser);
    }
    
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .<UserResponseDto>map(userMapper::toResponseDto)
                .toList();
    }
    
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        return userMapper.toResponseDto(user);
    }
    
    public UserResponseDto updateUser(UUID id, UserRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        
        validationService.validateEmail(request.getEmail());
        validationService.validatePassword(request.getPassword());
        
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("El correo ya registrado");
        }
        
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        
        user.getPhones().clear();
        List<Phone> phones = request.getPhones().stream()
                .<Phone>map(phoneDto -> Phone.builder()
                        .number(phoneDto.getNumber())
                        .citycode(phoneDto.getCitycode())
                        .contrycode(phoneDto.getContrycode())
                        .user(user)
                        .build())
                .toList();
        user.getPhones().addAll(phones);
        
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }
    
    public UserResponseDto patchUser(UUID id, UserRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        
        if (request.getEmail() != null) {
            validationService.validateEmail(request.getEmail());
            if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("El correo ya registrado");
            }
            user.setEmail(request.getEmail());
        }
        
        if (request.getPassword() != null) {
            validationService.validatePassword(request.getPassword());
            user.setPassword(request.getPassword());
        }
        
        if (request.getPhones() != null) {
            user.getPhones().clear();
            List<Phone> phones = request.getPhones().stream()
                    .<Phone>map(phoneDto -> Phone.builder()
                            .number(phoneDto.getNumber())
                            .citycode(phoneDto.getCitycode())
                            .contrycode(phoneDto.getContrycode())
                            .user(user)
                            .build())
                    .toList();
            user.getPhones().addAll(phones);
        }
        
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }
    
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}