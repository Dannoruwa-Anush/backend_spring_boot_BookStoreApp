package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>{
    //can add custom queries here
    
    //1.Select all books that are belong to specific category
    @Query("SELECT book FROM Book book WHERE book.category.id = :categoryId") //SELECT * FROM books WHERE category_id=:categoryId
    List<Book> findBooksByCategoryId(@Param("categoryId") Long categoryId);
}
