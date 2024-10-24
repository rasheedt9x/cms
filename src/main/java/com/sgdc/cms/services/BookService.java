package com.sgdc.cms.services;

import com.sgdc.cms.dto.BookDto;
import com.sgdc.cms.models.Book;
import com.sgdc.cms.models.Loan;
import com.sgdc.cms.repositories.BookRepository;
import com.sgdc.cms.repositories.LoanRepository;
import com.sgdc.cms.utils.StorageUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(BookService.class);

    private BookRepository bookRepository;
    private LoanRepository loanRepository;

    @Autowired
    public BookService(BookRepository bookRepo){
        this.bookRepository = bookRepo;
    }

	public LoanRepository getLoanRepository() {
		return loanRepository;
	}

    @Autowired
	public void setLoanRepository(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
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
                    
                    if (book.getImage() != null) {
                         byte[] imageBytes = StorageUtils.getImageBytes(book.getImage());
                    	String b64 = Base64.getEncoder().encodeToString(imageBytes);
                        bookDto.setImageBase64(b64);
                     
                    }
                    
                    return bookDto;
                })
                .collect(Collectors.toList());
    }


    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No book found with id")
        );
    }


    public void deleteBook(Long id) {
        Book b = bookRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Unable to delete -> book not found")
        );
        List<Loan> bookLoans = loanRepository.findAllByBook(b);
        for(Loan loan: bookLoans ) {
            try {
                loanRepository.delete(loan);
            } catch ( Exception e ) {
                e.printStackTrace();
                throw new RuntimeException("Deleting book -> unable to delete loan");
            }
        }

        try {
        	bookRepository.delete(b);
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException("Deleting book -> unable to delete book");
        }
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

 
        try {
            if (bookDto.getImageBase64() != null) {
                byte[] image = Base64.getDecoder().decode(bookDto.getImageBase64());
                String imagePath = StorageUtils.saveImageToStorage(image,"books",book.getId() + ".jpg");
            	book.setImage(imagePath);

            	book = bookRepository.save(book);
            } else {
                book.setImage(null);
            }
            
        } catch (Exception e ) {
            e.printStackTrace();
        }


        bookDto.setId(book.getId());
        if (book.getImage() != null) {        	
            bookDto.setImagePath(book.getImage());
        }
        
        bookDto.setImageBase64(null);
        return bookDto;
    }


}
