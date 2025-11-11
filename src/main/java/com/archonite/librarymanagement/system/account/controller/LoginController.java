package com.archonite.librarymanagement.system.account.controller;

import com.archonite.librarymanagement.system.account.dto.LoginRequest;
import com.archonite.librarymanagement.system.account.service.LoginService;
import com.archonite.librarymanagement.system.account.dto.SignUpRequestDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/test")
    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    public String apiTest(){
        return "Application Running";
    }

    @PostMapping("/auth/create")
    public ResponseEntity<String> createAccount(@Valid @RequestBody SignUpRequestDto dto) throws Exception {
        return ResponseEntity.ok(loginService.registerAccount(dto));
    }

    @PostMapping("/sign-in")
    @PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> login(@RequestBody LoginRequest dto) throws Exception {
        String token = loginService.login(dto);
        if (!StringUtils.hasText(token)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(token);
    }
}
