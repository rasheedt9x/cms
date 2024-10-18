package com.sgdc.cms.services;

import com.sgdc.cms.dto.BookDto;
import com.sgdc.cms.models.Book;
import com.sgdc.cms.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(BookService.class);

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepo){
        this.bookRepository = bookRepo;
    }

    public List<BookDto> retrieveAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> {
                    BookDto bookDto = new BookDto();
                    bookDto.setId(book.getId());
                    bookDto.setTitle(book.getTitle());
                    bookDto.setIsbn(book.getIsbn());
                    bookDto.setAvailableCopies(book.getAvailableCopies());
                    bookDto.setTotalCopies(book.getTotalCopies());
                    return bookDto;
                })
                .collect(Collectors.toList());
    }


    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No book found with id")
        );
    }

    public BookDto saveBook(BookDto bookDto){
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());
        book.setAvailableCopies(bookDto.getAvailableCopies());
        book.setTotalCopies(bookDto.getTotalCopies());
        try {
            book = bookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to save book");
        }

        bookDto.setId(book.getId());

        return bookDto;
    }


}
