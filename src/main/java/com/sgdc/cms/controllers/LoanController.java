package com.sgdc.cms.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgdc.cms.dto.LoanDto;
import com.sgdc.cms.services.LoanService;

@RestController
@RequestMapping("/api/v1/bookloan")
public class LoanController {
    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService){
        this.loanService = loanService;    
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestBook(@RequestBody LoanDto loanDto, @RequestHeader("Authorization") String token) {
        LoanDto statusDto = loanService.addBookRequest(loanDto, token.substring(7));
        Map<String, Object> map = new HashMap<>();
        map.put("loanId",statusDto.getId());
        map.put("bookId",statusDto.getBookId());       
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(loanDto);
    }    
}
