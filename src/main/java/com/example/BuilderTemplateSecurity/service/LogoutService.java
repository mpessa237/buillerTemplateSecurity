package com.example.BuilderTemplateSecurity.service;

import com.example.BuilderTemplateSecurity.configuration.JwtService;
import com.example.BuilderTemplateSecurity.entity.RevokedToken;
import com.example.BuilderTemplateSecurity.repository.RevokedTokenRepo;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final RevokedTokenRepo revokedTokenRepo;
    private final JwtService jwtService;

    @Transactional
    public void Logout(HttpServletRequest httpServletRequest){
        final String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader==null || !authHeader.startsWith("Bearer")){
            return;
        }

        String jwt = authHeader.substring(7);

        // Sauvegarder le token dans la liste noire avec sa durée de vie restante
        long expirationTime = jwtService.extractExpiration(jwt).getTime();

        RevokedToken revokedToken = RevokedToken.builder()
                .token(jwt)
                .expirationTime(expirationTime)
                .build();

        revokedTokenRepo.save(revokedToken);

        SecurityContextHolder.clearContext();
    }
}
