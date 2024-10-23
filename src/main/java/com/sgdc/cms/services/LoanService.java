package com.sgdc.cms.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.LoanDto;
import com.sgdc.cms.models.Book;
import com.sgdc.cms.models.Employee;
import com.sgdc.cms.models.Loan;
import com.sgdc.cms.models.LoanStatus;
import com.sgdc.cms.models.User;
import com.sgdc.cms.repositories.BookRepository;
import com.sgdc.cms.repositories.EmployeeRepository;
import com.sgdc.cms.repositories.LoanRepository;
import com.sgdc.cms.repositories.StudentRepository;
import com.sgdc.cms.security.jwt.JwtTokenProvider;

@Service
public class LoanService {

    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private LoanRepository loanRepository;
    private BookRepository bookRepository;
    private JwtTokenProvider jwtTokenProvider;
    private StudentRepository studentRepository;
    private EmployeeRepository employeeRepository;

    private ApplicationEventPublisher eventPublisher;

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
        loan.setLoanStatus(LoanStatus.PENDING_APPROVAL);
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
        loanDto.setLoanStatus(LoanStatus.PENDING_APPROVAL.toString());
        logger.info("Loan request processed successfully for book ID: {}", loanDto.getBookId());
        return loanDto;
    }

    public List<LoanDto> retrieveAllLoanApplications() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream()
                .map(loan -> {
                    return createLoanDtoFromLoan(loan);
                }).collect(Collectors.toList());
    }

    public LoanDto approveLoan(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Couldnt approve loan -> loan approval -> loan details not found"));

        if (loan.getLoanStatus() != LoanStatus.PENDING_APPROVAL) {
            throw new RuntimeException("Couldnt approve loan as its not pending approval");
        }
        loan.setStartDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(28));
        loan.setLoanStatus(LoanStatus.APPROVED);
        try {
            loan = loanRepository.save(loan);
        } catch (Exception e) {
            throw new RuntimeException("Couldnt approve loan -> error while saving loan application");
        }
        return createLoanDtoFromLoan(loan);
    }

    public LoanDto createLoanDtoFromLoan(Loan loan) {
        LoanDto loanDto = new LoanDto();
        loanDto.setId(loan.getId());
        loanDto.setBook(loan.getBook().getId());
        loanDto.setBookTitle(loan.getBook().getTitle());
        loanDto.setStartDate(loan.getStartDate() != null ? loan.getStartDate().toString() : null);
        loanDto.setDueDate(loan.getDueDate() != null ? loan.getDueDate().toString() : null);
        loanDto.setReturnDate(loan.getReturnDate() != null ? loan.getReturnDate().toString() : null);
        loanDto.setLoanStatus(loan.getLoanStatus().toString());

        String collegeId = null;
        String username = loan.getUser().getUsername();
        if (studentRepository.existsByUsername(username)) {
            collegeId = studentRepository.findByUsername(username).get().getStudentId();
        } else if (employeeRepository.existsByUsername(username)) {
            collegeId = employeeRepository.findByUsername(username).get().getEmployeeId();
        } else {
            throw new RuntimeException("Creating loan dto -> Unable to process");
        }
        loanDto.setCollegeId(collegeId);
        return loanDto;
    }

    public boolean isLibrarian(String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        Employee e = employeeRepository.findByUsername(username).orElseThrow(
            () -> new RuntimeException("Checking if librarian -> Emp not found")
        );
        logger.info("IsLibrarian -> Username: " + e.getEmployeeId());
        // for(Role r : e.getRoles()) {
        // logger.info(r.getRolename());
        // }
        boolean isLibrarian = e.getRoles().stream().anyMatch(role -> role.getRolename().equals("LIBRARIAN"));
        return isLibrarian;
    }

    public void addReturnRequest(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Loan return request -> book loan not found"));
        loan.setLoanStatus(LoanStatus.RETURN_REQUESTED);
        try {
            loanRepository.save(loan);
        } catch (Exception e) {
            throw new RuntimeException("Couldnt request return -> FAILED");
        }
    }

    public void approveReturn(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found for return approval"));

        if (loan.getLoanStatus() != LoanStatus.RETURN_REQUESTED) {
            throw new RuntimeException("Loan is not in a return-requested state");
        }

        loan.setLoanStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        loanRepository.save(loan);
    }

    public List<LoanDto> retriveAllLoansByUser(String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user = null;
        if (studentRepository.existsByUsername(username)) {
            user = studentRepository.findByUsername(username).get();
        } else if (employeeRepository.existsByUsername(username)) {
            user = employeeRepository.findByUsername(username).get();
        }
        List<Loan> allLoans = loanRepository.findAllByUser(user);
        List<LoanDto> dtos = new ArrayList<>();

        for (Loan loan : allLoans) {
            dtos.add(createLoanDtoFromLoan(loan));
        }
        return dtos;
    }

    public LoanDto renewLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found for return approval"));
        if (!loan.isOverdue() && loan.getLoanStatus() == LoanStatus.APPROVED) {
            try {
            	loan.setDueDate(loan.getDueDate().plusDays(15));
            	loan = loanRepository.save(loan);
            } catch (Exception e) {
                throw new RuntimeException("Unable to renew loan");
            }
        }
        return createLoanDtoFromLoan(loan);
    }

    // @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedDelay = 1000 * 60)
    public void overdueLoans() {
        checkForOverdueLoans();
    }

    public void checkForOverdueLoans() {
        List<Loan> loans = loanRepository.findAll();
        for (Loan loan : loans) {
            if (loan.isOverdue()) {
                loan.setLoanStatus(LoanStatus.OVERDUE);
                loanRepository.save(loan);
            }
        }
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

    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Autowired
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

}
