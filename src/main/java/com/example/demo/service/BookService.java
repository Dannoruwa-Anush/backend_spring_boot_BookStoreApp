package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;

@Service
public interface BookService {
    //interface methods are implicitly abstract and public
    List<Book> getAllBooks(); //List-> import java.util.List;
    Book getBookById(long id);
    Book createBook(Book book);
    Book updateBook(long id, BookDTO bookDTO);
    void deleteBook(long id);
}
