package com.sgdc.cms.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgdc.cms.dto.LoanDto;
import com.sgdc.cms.services.LoanService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/bookloan")
public class LoanController {
    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestBook(@RequestBody LoanDto loanDto, @RequestHeader("Authorization") String token) {
        logger.info("Requesting book loan: {}", loanDto);
        LoanDto statusDto = loanService.addBookRequest(loanDto, token.substring(7));
        Map<String, Object> map = new HashMap<>();
        map.put("loanId", statusDto.getId());
        map.put("bookId", statusDto.getBookId());
        logger.info("Book loan requested successfully: loanId={}, bookId={}", statusDto.getId(), statusDto.getBookId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(loanDto);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLoanApplications() {
        logger.info("Retrieving all loan applications");
        List<LoanDto> dtos = loanService.retrieveAllLoanApplications();
        logger.info("Retrieved {} loan applications", dtos.size());
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approveLoan(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        logger.info("Approving loan with token: {}", token);

        if (token == null || !loanService.isLibrarian(token.substring(7))) {
            logger.error("Could not approve loan -> Employee is not a librarian or token is missing");
            throw new RuntimeException("Could not approve loan -> Employee is not a librarian");
        }

        logger.info("Loan approved successfully");
        return ResponseEntity.ok("");
    }
}

