package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    //can add custom queries here.

    //1.find by role name
    Role findRoleByName(String roleName);
}
