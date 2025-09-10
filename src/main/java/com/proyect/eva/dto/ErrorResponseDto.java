package com.proyect.eva.dto;

public class ErrorResponseDto {
    
    private String mensaje;
    
    public ErrorResponseDto() {}
    
    public ErrorResponseDto(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}