package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserLoginDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.payloads.responses.JwtResponse;
import com.example.demo.payloads.responses.MessageResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;

@CrossOrigin(origins = "*")
@RestController
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        
        if(userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken"));
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email already in use"));
        }
        
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        //role setting
        //Note: sql query: INSERT INTO roles (name) VALUES ('ROLE_USER');

        Role defaultRole = roleRepository.findRoleByName("ROLE_USER"); // Fetch the default user role
        if (defaultRole != null) {
            newUser.setRole(defaultRole);
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Default role not found"));
        }
        
        userRepository.save(newUser);

        return ResponseEntity.ok(new MessageResponse("User created successfully."));
    }
    //--- 
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        String roleName = user.getRole() != null ? user.getRole().getName() : "No Role Assigned";

        return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail(), roleName));
    }
    //---  
}
