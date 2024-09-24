package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data //Generate getters & setters
public class BookDTO {
    private String name;
    private Double price;
    private String description;
    private MultipartFile frontImage;
}
