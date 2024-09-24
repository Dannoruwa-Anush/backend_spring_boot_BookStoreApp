package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "orders") //Set table name
@Entity
@Data //Generate getters & setters
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private String status;

    //Get the system time on Order create and update
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    //----- [Start - Entity mapping ] ------------------------
    //user(1) --- order(M)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") //Foreign key of User Entity
    private User user;

    //order(M) --- book(M)
    @ManyToMany(cascade = { CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "orders_books",
        joinColumns = { @JoinColumn(name = "order_id")},
        inverseJoinColumns = { @JoinColumn(name = "book_id") }
    )
    private Set<Book> books = new HashSet<>();
    //----- [End - Entity mapping ] ---------------------------
}
