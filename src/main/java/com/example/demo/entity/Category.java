package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "categories") //Set table name
@Entity
@Data //Generate getters & setters
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //set primary key automatically
    private long id;

    @Column(nullable = false)
    private String name;

    //----- [Start - Entity mapping ] ------------------------
    //Books(M) --- Category(1)
    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL}) //category ->private Category category;  in Book.java
    private List<Book> books;
    //----- [End - Entity mapping ] --------------------------
}
