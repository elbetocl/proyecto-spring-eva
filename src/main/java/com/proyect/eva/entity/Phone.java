package com.proyect.eva.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "phones")
public class Phone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String number;
    
    @Column(nullable = false)
    private String citycode;
    
    @Column(nullable = false)
    private String contrycode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    public Phone() {}
    
    public Phone(String number, String citycode, String contrycode) {
        this.number = number;
        this.citycode = citycode;
        this.contrycode = contrycode;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    
    public String getCitycode() { return citycode; }
    public void setCitycode(String citycode) { this.citycode = citycode; }
    
    public String getContrycode() { return contrycode; }
    public void setContrycode(String contrycode) { this.contrycode = contrycode; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}