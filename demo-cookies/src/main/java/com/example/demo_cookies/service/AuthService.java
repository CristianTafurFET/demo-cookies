package com.example.demo_cookies.service;

import com.example.demo_cookies.dto.AuthResponse;
import com.example.demo_cookies.dto.LoginRequest;
import com.example.demo_cookies.dto.RegisterRequest;
import com.example.demo_cookies.model.User;
import com.example.demo_cookies.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse registrar(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El usuario '" + request.getUsername() + "' ya existe.");
        }
        User nuevoUsuario = new User();
        nuevoUsuario.setUsername(request.getUsername());
        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(nuevoUsuario);
        return new AuthResponse(jwtService.generarToken(nuevoUsuario), "Usuario registrado exitosamente.");
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User usuario = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        return new AuthResponse(jwtService.generarToken(usuario), "Login exitoso.");
    }
}