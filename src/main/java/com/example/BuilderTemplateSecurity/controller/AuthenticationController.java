package com.example.BuilderTemplateSecurity.controller;

import com.example.BuilderTemplateSecurity.dto.LoginRequest;
import com.example.BuilderTemplateSecurity.dto.LoginResponse;
import com.example.BuilderTemplateSecurity.dto.RegisterRequest;
import com.example.BuilderTemplateSecurity.service.AuthenticationService;
import com.example.BuilderTemplateSecurity.service.LoginService;
import com.example.BuilderTemplateSecurity.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:54044"})
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody RegisterRequest registerRequest){
        authenticationService.register(registerRequest);
        return ResponseEntity.ok("register successful!!");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@Validated @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(loginService.authenticate(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest){
        logoutService.Logout(httpServletRequest);
        return ResponseEntity.ok("Logout successfully!");

    }
}
