package com.proyect.eva.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserResponseDto {
    
    private UUID id;
    private String name;
    private String email;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime created;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime modified;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime last_login;
    
    private String token;
    private Boolean isactive;
    private List<PhoneDto> phones;
    
    public UserResponseDto() {}
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }
    
    public LocalDateTime getModified() { return modified; }
    public void setModified(LocalDateTime modified) { this.modified = modified; }
    
    public LocalDateTime getLast_login() { return last_login; }
    public void setLast_login(LocalDateTime last_login) { this.last_login = last_login; }
    
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public Boolean getIsactive() { return isactive; }
    public void setIsactive(Boolean isactive) { this.isactive = isactive; }
    
    public List<PhoneDto> getPhones() { return phones; }
    public void setPhones(List<PhoneDto> phones) { this.phones = phones; }
}