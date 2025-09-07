package com.example.BuilderTemplateSecurity.controller;

import com.example.BuilderTemplateSecurity.dto.LoginRequest;
import com.example.BuilderTemplateSecurity.dto.LoginResponse;
import com.example.BuilderTemplateSecurity.dto.RegisterRequest;
import com.example.BuilderTemplateSecurity.service.AuthenticationService;
import com.example.BuilderTemplateSecurity.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody RegisterRequest registerRequest){
        authenticationService.register(registerRequest);
        return ResponseEntity.ok("register successful!!");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@Validated @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest){
        logoutService.Logout(httpServletRequest);
        return ResponseEntity.ok("Logout successfully!");

    }
}
