package com.sgdc.cms.repositories;

import com.sgdc.cms.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface
LoanRepository extends JpaRepository<Loan, Long > {
}
