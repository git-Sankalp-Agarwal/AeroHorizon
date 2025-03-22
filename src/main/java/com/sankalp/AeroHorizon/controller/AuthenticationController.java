package com.sankalp.AeroHorizon.controller;


import com.sankalp.AeroHorizon.dto.AuthenticationRequest;
import com.sankalp.AeroHorizon.dto.AuthenticationResponse;
import com.sankalp.AeroHorizon.dto.RegisterRequest;
import com.sankalp.AeroHorizon.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "User/login";
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "User/registration";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.registerUser(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}

