package com.hotelier.service;

import com.hotelier.dto.AuthResponse;
import com.hotelier.dto.LoginRequest;
import com.hotelier.dto.RegisterRequest;
import com.hotelier.model.User;
import com.hotelier.repository.UserRepository;
import com.hotelier.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${app.magic-login.base-url}")
    private String magicLoginBaseUrl;

    @Value("${app.magic-login.expiry-hours}")
    private long magicLoginExpiryHours;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole(User.Role.GUEST);
        user.setRoomNumber(request.getRoomNumber());
        user.setPhoneNumber(request.getPhoneNumber());

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());

        return new AuthResponse(token, user.getEmail(), user.getName(), user.getRole().name(), user.getId());
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());

        return new AuthResponse(token, user.getEmail(), user.getName(), user.getRole().name(), user.getId());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public AuthResponse passwordlessRegister(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 1. Create user as usual
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // you can ignore later if going full passwordless
        user.setName(request.getName());
        user.setRole(User.Role.GUEST);
        user.setRoomNumber(request.getRoomNumber());
        user.setPhoneNumber(request.getPhoneNumber());

        // 2. Generate a random magic-login token
        String rawToken = UUID.randomUUID().toString().replace("-", "");

        // 3. Set token + expiry on user
        user.setMagicLoginToken(rawToken);
        user.setMagicLoginTokenExpiry(
                LocalDateTime.now().plusHours(magicLoginExpiryHours)
        );

        // 4. Save user with token
        user = userRepository.save(user);

        // 5. Build magic link to send to user’s email
        // final URL will look like: https://your-frontend.com/auth/magic-login?token=<rawToken>
        String magicLink = magicLoginBaseUrl + "?token=" + rawToken;

        // 6. Send email with the magic login link
        //emailService.sendMagicLoginLink(user.getEmail(), user.getName(), magicLink);
        log.info("Magic login link for {}: {}", user.getEmail(), magicLink);

        // 7. For magic-link flow you usually don't return a JWT here.
        //    You can return a response saying "check your email" and token = null.
        return new AuthResponse(
                rawToken,                        // TBD: in real app, token would be null here, but for testing we return the raw token
                user.getEmail(),
                user.getName(),
                user.getRole().name(),
                user.getId()
        );
    }

    public AuthResponse loginWithMagicToken(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            throw new RuntimeException("Invalid magic login token");
        }

        User user = userRepository.findByMagicLoginToken(rawToken)
                .orElseThrow(() -> new RuntimeException("Invalid or expired login link"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = user.getMagicLoginTokenExpiry();

        if (expiry == null || expiry.isBefore(now)) {
            throw new RuntimeException("Login link has expired");
        }

        // Invalidate token so link can't be reused (one-time use)
        user.setMagicLoginToken(null);
        user.setMagicLoginTokenExpiry(null);
        userRepository.save(user);

        // Generate normal JWT for the user
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getId()
        );

        // Return same AuthResponse shape you already use
        return new AuthResponse(
                token,
                user.getEmail(),
                user.getName(),
                user.getRole().name(),
                user.getId()
        );
    }
}

