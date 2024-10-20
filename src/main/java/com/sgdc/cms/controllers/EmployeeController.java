package com.sgdc.cms.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgdc.cms.dto.EmployeeDto;
import com.sgdc.cms.services.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * EmployeeController
 */
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private EmployeeService employeeService;
    
    @Autowired
    public EmployeeController(EmployeeService es) {
        this.employeeService = es;
    }

    

    @GetMapping("/get/self")
    public ResponseEntity<?> getStudentDetailsByToken(Principal principal,HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        String token = auth.substring(7);
        EmployeeDto dto = employeeService.retrieveEmployeeByToken(token);
        return ResponseEntity.ok().body(dto);
    }


    @PostMapping("/new")    
	public ResponseEntity<?> newEmployee(@RequestBody EmployeeDto dto) {
	    Object object = employeeService.saveEmployee(dto);
	    return ResponseEntity.ok(object.toString());
	}
}
