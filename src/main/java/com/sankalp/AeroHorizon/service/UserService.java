package com.sankalp.AeroHorizon.service;


import com.sankalp.AeroHorizon.dto.AuthenticationRequest;
import com.sankalp.AeroHorizon.entity.User;
import com.sankalp.AeroHorizon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User u){userRepository.save(u);}
    
    public List<User> getAllUsers() {
        List<User> users =userRepository.findAll();
        ResponseEntity.ok();
        return users;
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException("User with id " + userId +
                " not found"));
    }

    public boolean checkUserRoleIsAdmin(AuthenticationRequest request) {
        User admin = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return admin.getRoles()
                    .toString()
                    .equals("ROLE_ADMIN");
    }
}
