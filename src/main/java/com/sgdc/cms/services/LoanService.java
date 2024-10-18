package com.sgdc.cms.services;

import com.sgdc.cms.dto.BookDto;
import com.sgdc.cms.dto.LoanDto;
import com.sgdc.cms.models.Loan;
import com.sgdc.cms.repositories.BookRepository;
import com.sgdc.cms.repositories.EmployeeRepository;
import com.sgdc.cms.repositories.LoanRepository;
import com.sgdc.cms.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private LoanRepository loanRepository;
    private BookRepository bookRepository;

    private StudentRepository studentRepository;
    private EmployeeRepository employeeRepository;

    public LoanService(LoanRepository lr, BookRepository br) {
        this.loanRepository = lr;
        this.bookRepository = br;
    }



    public boolean lendBook(LoanDto loanDto) {
        Loan loan = new Loan();
        String collegeId = loanDto.getUsername();
//        if (studentRepository.existsByUsername(username)) {
//            loan.setUser(stude);
//        }

        return true;
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


}
