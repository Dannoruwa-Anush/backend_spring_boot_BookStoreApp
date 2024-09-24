package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;

@Service
public interface CategoryService {
    //interface methods are implicitly abstract and public
    List<Category> getAllCategories(); //List-> import java.util.List;
    Category getCategoryById(long id);
    Category createCategory(Category category);
    Category updateCategory(long id, Category category);
    void deleteCategory(long id);        
}
