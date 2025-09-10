package com.proyect.eva.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private LocalDateTime created;
    
    @Column(nullable = false)
    private LocalDateTime modified;
    
    @Column(name = "last_login", nullable = false)
    private LocalDateTime lastLogin;
    
    @Column(nullable = false)
    private String token;
    
    @Column(nullable = false)
    private Boolean isactive;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones = new ArrayList<>();
    
    public User() {}
    
    public User(String name, String email, String password, String token) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.token = token;
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
        this.isactive = true;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.modified = LocalDateTime.now();
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }
    
    public LocalDateTime getModified() { return modified; }
    public void setModified(LocalDateTime modified) { this.modified = modified; }
    
    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public Boolean getIsactive() { return isactive; }
    public void setIsactive(Boolean isactive) { this.isactive = isactive; }
    
    public List<Phone> getPhones() { return phones; }
    public void setPhones(List<Phone> phones) { this.phones = phones; }
}