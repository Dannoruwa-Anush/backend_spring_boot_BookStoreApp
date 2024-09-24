package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;

@Service
public interface UserService {
        //interface methods are implicitly abstract and public

        List<User> getAllUsers(); //List-> import java.util.List;
        User getUserById(long id);
        User saveUser(User user);
        User updateUser(long id, User user);
        void deleteUser(long id);        
}
