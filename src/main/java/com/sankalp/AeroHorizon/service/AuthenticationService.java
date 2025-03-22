package com.sankalp.AeroHorizon.service;


import com.sankalp.AeroHorizon.User.Role;
import com.sankalp.AeroHorizon.config.JWTService;
import com.sankalp.AeroHorizon.dto.AuthenticationRequest;
import com.sankalp.AeroHorizon.dto.AuthenticationResponse;
import com.sankalp.AeroHorizon.dto.RegisterRequest;
import com.sankalp.AeroHorizon.entity.User;
import com.sankalp.AeroHorizon.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse registerUser(RegisterRequest request) {
        User user = User.builder()
                       .firstName(request.getFirstname())
                       .lastName(request.getLastname())
                       .email(request.getEmail())
                       .password(passwordEncoder.encode(request.getPassword()))
                       .roles(Set.of(Role.ROLE_USER))
                       .build();
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

       if(existingUser.isPresent()){
           return null;
       }
        userRepository.save(user);
        String jwtToken = jwtService.generateAccessToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.ROLE_ADMIN))
                .build();
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent()){
            return null;
        }
        userRepository.save(user);
        String jwtToken = jwtService.generateAccessToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateAccessToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}

    
