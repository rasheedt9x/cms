package com.sgdc.cms.services;

import com.sgdc.cms.repositories.BookRepository;
import com.sgdc.cms.repositories.LoanRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private LoanRepository loanRepository;
    private BookRepository bookRepository;

    public LoanService(LoanRepository lr, BookRepository br) {
        this.loanRepository = lr;
        this.bookRepository = br;
    }



}
