package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "books") //Set table name
@Entity
@Data //Generate getters & setters
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //set primary key automatically
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String frontImage;

    //----- [Start - Entity mapping ] ------------------------
    //Books(M) --- Category(1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") //category_id
    private Category category;    
    //----- [End - Entity mapping ] ------------------------
}
