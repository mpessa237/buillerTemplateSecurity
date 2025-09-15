package com.example.BuilderTemplateSecurity.service;

import com.example.BuilderTemplateSecurity.configuration.JwtService;
import com.example.BuilderTemplateSecurity.dto.LoginRequest;
import com.example.BuilderTemplateSecurity.dto.LoginResponse;
import com.example.BuilderTemplateSecurity.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager ;

    public LoginResponse authenticate(LoginRequest loginRequest){
        // Authentifier l'utilisateur en utilisant le gestionnaire d'authentification de Spring.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("user not found!!"));

        //gerer le token
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        return loginResponse;
    }
}
