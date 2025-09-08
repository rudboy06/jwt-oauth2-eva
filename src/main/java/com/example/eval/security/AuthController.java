package com.example.eval.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String token = jwtService.generateToken(request.getUsername());
            long expiresSec = 60L * 15L;
            return new TokenResponse(token, expiresSec);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciales inválidas");
        }
    }
}
