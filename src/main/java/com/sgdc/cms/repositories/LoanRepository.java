package com.sgdc.cms.repositories;

import com.sgdc.cms.models.Book;
import com.sgdc.cms.models.Loan;
import com.sgdc.cms.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface
LoanRepository extends JpaRepository<Loan, Long > {

    public List<Loan> findAllByUser(User user);

    public List<Loan> findAllByBook(Book book);
}
