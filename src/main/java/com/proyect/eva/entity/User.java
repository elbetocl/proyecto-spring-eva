package com.proyect.eva.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Builder.Default
    private List<Phone> phones = new ArrayList<>();
    
    @PreUpdate
    public void preUpdate() {
        this.modified = LocalDateTime.now();
    }
}