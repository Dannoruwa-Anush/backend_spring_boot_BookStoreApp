package com.example.demo.service;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;

import java.io.IOException;

@Service
public class BookServiceImpl implements BookService{
    
    @Autowired
    BookRepository bookRepository;

    @Value("${upload.directory}") //gets value of variable defined in the resources/application.properties
    private String uploadDirectory;
    
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book is not found" + id));
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id).orElse(null);

        if(existingBook != null) {
            MultipartFile file = bookDTO.getFrontImage();
            String filename = file.getOriginalFilename();
            String filePath = uploadDirectory + File.separator + filename;

            try {
                file.transferTo(new File(filePath));
            } catch (IllegalStateException e) {
                
                e.printStackTrace();
            } catch (IOException e) {
                
                e.printStackTrace();
            }

            //change existing book according to our requirement
            existingBook.setFrontImage(filename);
          
            existingBook.setName(bookDTO.getName());
            existingBook.setPrice(bookDTO.getPrice());
            existingBook.setDescription(bookDTO.getDescription());
            return bookRepository.save(existingBook);
        }

        return null;
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }   
}
