package com.example.demo.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "roles") //Set table name
@Entity
@Data //Generate getters & setters
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    //----- [Start - Entity mapping ] ------------------------
    //User(M) --- Role(1)
    @OneToMany(mappedBy = "role")
    private Set<User> users; // A role can have many users
    //----- [End - Entity mapping ] ------------------------
}
