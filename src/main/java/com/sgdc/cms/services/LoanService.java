package com.sgdc.cms.services;

import com.sgdc.cms.dto.BookDto;
import com.sgdc.cms.dto.LoanDto;
import com.sgdc.cms.models.Book;
import com.sgdc.cms.models.Employee;
import com.sgdc.cms.models.Loan;
import com.sgdc.cms.models.Role;
import com.sgdc.cms.repositories.BookRepository;
import com.sgdc.cms.repositories.EmployeeRepository;
import com.sgdc.cms.repositories.LoanRepository;
import com.sgdc.cms.repositories.StudentRepository;
import com.sgdc.cms.security.jwt.JwtTokenProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private LoanRepository loanRepository;
    private BookRepository bookRepository;
    private JwtTokenProvider jwtTokenProvider;
    private StudentRepository studentRepository;
    private EmployeeRepository employeeRepository;

    public LoanService(LoanRepository lr, BookRepository br) {
        this.loanRepository = lr;
        this.bookRepository = br;
    }

    public LoanDto addBookRequest(LoanDto loanDto, String token) {
        logger.info("Processing loan request for book ID: {}", loanDto.getBookId());
        Loan loan = new Loan();
        String username = jwtTokenProvider.getUsernameFromToken(token);
        logger.debug("Extracted username from token: {}", username);

        if (studentRepository.existsByUsername(username)) {
            loan.setUser(studentRepository.findByUsername(username).get());
            logger.info("User {} is a student.", username);
        } else if (employeeRepository.existsByUsername(username)) {
            loan.setUser(employeeRepository.findByUsername(username).get());
            logger.info("User {} is an employee.", username);
        } else {
            logger.error("User not found: {}", username);
            throw new RuntimeException("Couldnt apply for book -> user not found");
        }

        Book book = bookRepository.findById(loanDto.getBookId()).orElseThrow(
                () -> {
                    logger.error("Book not found with ID: {}", loanDto.getBookId());
                    return new RuntimeException("Couldnt apply for book -> Book not found");
                });

        if (book.getAvailableCopies() <= 0) {
            logger.error("No available copies for book ID: {}", loanDto.getBookId());
            throw new RuntimeException("Couldnt apply for book -> Copies not available");
        }

        loan.setBook(book);
        loan.setStartDate(null);
        loan.setDueDate(null);
        loan.setReturnDate(null);

        try {
            loan = loanRepository.save(loan);
            logger.info("Loan application saved successfully with ID: {}", loan.getId());
        } catch (Exception e) {
            logger.error("Error while saving loan application", e);
            throw new RuntimeException("Couldnt apply for book -> Error while saving loan application");
        }
        try {
            int copies = book.getAvailableCopies();
            book.setAvailableCopies(copies - 1 < 0 ? 0 : copies - 1);
            bookRepository.save(book);
        } catch (Exception e) {
            logger.error("Error while checking available copies", e);
            loanRepository.delete(loan);
            throw new RuntimeException("Couldnt apply for book -> Error while saving loan application (book updation)");
        }
        // Set values back into Dto
        loanDto.setId(loan.getId());
        loanDto.setStartDate(null);
        loanDto.setReturnDate(null);
        loan.setApproved(false);
        logger.info("Loan request processed successfully for book ID: {}", loanDto.getBookId());
        return loanDto;
    }

    public List<LoanDto> retrieveAllLoanApplications() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream()
                    .map(loan -> {
                        LoanDto dto = new LoanDto();
                        dto.setId(loan.getId());
                        dto.setBook(loan.getBook().getId());
                        dto.setBookTitle(loan.getBook().getTitle());
                        return dto;
                    }).collect(Collectors.toList());
    }

    public boolean isLibrarian(String token){
        String username = jwtTokenProvider.getUsernameFromToken(token);       
        Employee e = employeeRepository.findByUsername(username).get();
        logger.info("IsLibrarian -> Username: "+ e.getEmployeeId());
        // for(Role r : e.getRoles()) {
        //     logger.info(r.getRolename());
        // }
        boolean isLibrarian = e.getRoles().stream().anyMatch(role -> role.getRolename().equals("LIBRARIAN"));
        return isLibrarian;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public JwtTokenProvider getJwtTokenProvider() {
        return jwtTokenProvider;
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
}
