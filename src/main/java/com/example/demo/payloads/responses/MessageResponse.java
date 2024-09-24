package com.example.demo.payloads.responses;

import lombok.Data;

@Data //Generate Getter & Setters using Lombok 
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
