package com.example.demo_cookies.dto;

public class AuthResponse {
    private String token;
    private String mensaje;

    public AuthResponse(String token, String mensaje) {
        this.token = token;
        this.mensaje = mensaje;
    }

    public String getToken() { return token; }
    public String getMensaje() { return mensaje; }
}