package com.sgdc.cms.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgdc.cms.dto.EmployeeDto;
import com.sgdc.cms.dto.UpdateUserDto;
import com.sgdc.cms.models.Student;
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

    @GetMapping("/get/self/dept/students")
    public ResponseEntity<?> getAllStudentsInDept(Principal principal,HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        String token = auth.substring(7);
        List<Student> l = employeeService.retriveStudentsinDepartment(token);
        return ResponseEntity.ok().body(l);
    }


    @PostMapping("/new")    
	public ResponseEntity<?> newEmployee(@RequestBody EmployeeDto dto) {
	    EmployeeDto object = employeeService.saveEmployee(dto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(object);
	}


	@PostMapping("/get/updateProfile")
	public ResponseEntity<?> changePassOrEmail (@RequestBody UpdateUserDto dto,@RequestHeader("Authorization") String token) {
        boolean updated = employeeService.changePasswordOrEmail(dto, token.substring(7));
	    return ResponseEntity.status(HttpStatus.OK).body("Password or Email Changed Successfully");
	}
}
