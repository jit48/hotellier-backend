package com.hotelier.controller;

import com.hotelier.dto.AuthResponse;
import com.hotelier.dto.LoginRequest;
import com.hotelier.dto.RegisterRequest;
import com.hotelier.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/passwordlessRegister")
    public ResponseEntity<AuthResponse> passwordlessRegister(@Valid @RequestBody RegisterRequest request) {
        log.info("Passwordless registration attempt for email: {}", request.getEmail());
        AuthResponse response = userService.passwordlessRegister(request);
        log.info("Passwordless registration email sent to: {}", request.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/passwordlessLogin")
    public ResponseEntity<AuthResponse> magicLogin(@RequestParam("token") String token) {
        AuthResponse response = userService.loginWithMagicToken(token);
        return ResponseEntity.ok(response);
    }
}

