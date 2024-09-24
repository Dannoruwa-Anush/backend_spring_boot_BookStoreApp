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

@Table(name = "users") //Set table name
@Entity
@Data //generate Getters and Setters using Lombok
public class User {
    //Define columns in table

    @Id //set primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //set primary key automatically
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    //----- [Start - Entity mapping ] ------------------------
    //User(M) --- Role(1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id") //role_id
    private Role role;    
    //----- [End - Entity mapping ] ------------------------
}
