package com.rj.bookordersystem.controller;

import com.rj.bookordersystem.dto.LoginRequest;
import com.rj.bookordersystem.dto.LoginResponse;
import com.rj.bookordersystem.dto.RegisterRequest;
import com.rj.bookordersystem.service.AuthenticationService;
import com.rj.bookordersystem.service.UserManageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserManageService userManageService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateLoginRequest(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return userManageService.create(request);
    }
}
