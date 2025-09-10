package com.proyect.eva.dto;

import jakarta.validation.constraints.NotBlank;

public class PhoneDto {
    
    @NotBlank(message = "Number is required")
    private String number;
    
    @NotBlank(message = "City code is required")
    private String citycode;
    
    @NotBlank(message = "Country code is required")
    private String contrycode;
    
    public PhoneDto() {}
    
    public PhoneDto(String number, String citycode, String contrycode) {
        this.number = number;
        this.citycode = citycode;
        this.contrycode = contrycode;
    }
    
    // Getters and Setters
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    
    public String getCitycode() { return citycode; }
    public void setCitycode(String citycode) { this.citycode = citycode; }
    
    public String getContrycode() { return contrycode; }
    public void setContrycode(String contrycode) { this.contrycode = contrycode; }
}