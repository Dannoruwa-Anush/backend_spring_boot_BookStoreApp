package com.example.demo.dto;

import lombok.Data;

@Data //Generate Getter & Setters using Lombok 
public class UserLoginDTO {
    private String username;
    private String password;
}
