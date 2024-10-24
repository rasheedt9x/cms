package com.sgdc.cms.controllers;

import com.sgdc.cms.dto.BookDto;
import com.sgdc.cms.repositories.BookRepository;
import com.sgdc.cms.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    private BookService bookService;
    
    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping(value = "/new", consumes = "application/json")
    public ResponseEntity<?> registerNewBook (@RequestBody BookDto bookDto) {
        BookDto savedBookDto = bookService.saveBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookDto);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.retrieveAllBooks());
    }

}
