package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
      //can add custom queries here.

      //1.find user by username
      Optional<User> findByUsername(String username);

      //2.exists by username
      Boolean existsByUsername(String username);

      //3.exists by email
      Boolean existsByEmail(String email);
}
