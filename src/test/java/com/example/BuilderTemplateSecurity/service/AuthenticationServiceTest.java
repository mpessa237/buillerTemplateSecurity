package com.example.BuilderTemplateSecurity.service;

import com.example.BuilderTemplateSecurity.configuration.JwtService;
import com.example.BuilderTemplateSecurity.dto.RegisterRequest;
import com.example.BuilderTemplateSecurity.entity.User;
import com.example.BuilderTemplateSecurity.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testRegisterShouldSaveNewUserWhenEmailIsUnique(){
        //Given(etat donner)
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("seven");
        registerRequest.setLastname("herve");
        registerRequest.setEmail("herve7@gmail.com");
        registerRequest.setPassword("password123");

        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        //When(quand)
        authenticationService.register(registerRequest);

        //Then(alors)
        verify(userRepo,times(1)).findByEmail("herve7@gmail.com");

        verify(userRepo,times(1)).save(any(User.class));
    }

    @Test
    void testRegisterShouldThrowExceptionWhenEmailIsAlreadyRegistered(){
        //Given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("existing.user@test.com");

        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        //When && Then
        assertThrows(IllegalArgumentException.class,()->{
            authenticationService.register(registerRequest);
        });

        verify(userRepo,never()).save(any(User.class));
    }

}