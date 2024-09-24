package com.example.demo.payloads.requests;

import lombok.Data;

@Data //Generate Getter & Setters using Lombok 
public class LoginRequest {
    private String username;
    private String password;
}
