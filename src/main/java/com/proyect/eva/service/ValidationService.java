package com.proyect.eva.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class ValidationService {
    
    @Value("${app.validation.email-regex}")
    private String emailRegex;
    
    @Value("${app.validation.password-regex}")
    private String passwordRegex;
    
    public void validateEmail(String email) {
        if (!Pattern.matches(emailRegex, email)) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
    }
    
    public void validatePassword(String password) {
        if (!Pattern.matches(passwordRegex, password)) {
            throw new IllegalArgumentException("Formato de contraseña inválido");
        }
    }
}