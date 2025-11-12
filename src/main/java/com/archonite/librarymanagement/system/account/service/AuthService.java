package com.archonite.librarymanagement.system.account.service;

import com.archonite.librarymanagement.system.account.dto.LoginRequest;
import com.archonite.librarymanagement.system.account.exception.InvalidCredentialsException;
import com.archonite.librarymanagement.system.account.util.JwtUtility;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtility jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtility jwtUtil) {
        this.userService = userService;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
    }

    public Optional<String> authenticate(LoginRequest loginRequestDto) throws InvalidCredentialsException {
        Optional<String> token = userService.findByUserName(loginRequestDto.userName())
                .filter(u -> passwordEncoder.matches(loginRequestDto.password(), u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getUserName(), String.valueOf(u.getRole())));

        return token;
    }

    public boolean validateToken(String token) {
        try{
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
