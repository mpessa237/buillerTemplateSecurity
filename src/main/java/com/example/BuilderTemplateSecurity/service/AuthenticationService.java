package com.example.BuilderTemplateSecurity.service;

import com.example.BuilderTemplateSecurity.configuration.JwtService;
import com.example.BuilderTemplateSecurity.dto.RegisterRequest;
import com.example.BuilderTemplateSecurity.entity.Role;
import com.example.BuilderTemplateSecurity.entity.User;
import com.example.BuilderTemplateSecurity.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest registerRequest){
        // verifier si user exist deja
        if (userRepo.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already registered");
        }
        //entre les infos du user
        User user = new User();
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Set.of(Role.ADMIN));
        user.setEnabled(true);
        user.setAccountLocked(false);

        userRepo.save(user);
    }

}
