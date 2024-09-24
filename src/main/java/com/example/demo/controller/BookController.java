package com.example.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

@CrossOrigin(origins = "*")
@RequestMapping("/book") //Servers entire controller under /book URI
@RestController
public class BookController {
    
    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }
    //---

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id){
        try {
            //Try to find book by id
            Book book = bookService.getBookById(id);
            return ResponseEntity.status(HttpStatus.OK).body(book);

        } catch (NoSuchElementException e) {
            //If there is no book by id, throw NoSuchElementException from BookServiceIml.java
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    //---

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book){
          try {
            //Try to find book by id
            Book bookControll = bookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookControll);

        } catch (NoSuchElementException e) {
            //If there is no book by id, throw NoSuchElementException from BookServiceIml.java
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    //---

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody BookDTO bookDTO){
        try {
            Book updateBook = bookService.updateBook(id, bookDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updateBook);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    //---

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id){
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
    //---
}
