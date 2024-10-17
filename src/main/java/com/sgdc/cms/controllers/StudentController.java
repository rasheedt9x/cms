package com.sgdc.cms.controllers;



import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sgdc.cms.dto.StudentDto;
import com.sgdc.cms.models.Student;
import com.sgdc.cms.services.StudentService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * StudentController
 */
@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService s){
        this.studentService = s;
    }

    @GetMapping(path = "/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok("Pong");
    }

    @GetMapping("/get/self")
    public ResponseEntity<?> getStudentDetailsByToken(Principal principal,HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        String token = auth.substring(7);
        Student std = studentService.findStudentByToken(token);
        return ResponseEntity.ok().body(std);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") String studentId) {
//        Student s = studentService.findStudentByStudentId(studentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/new")
    public ResponseEntity<?> addStudent(@RequestBody StudentDto dto){
        Object obj = studentService.saveStudent(dto);
        return ResponseEntity.ok(obj.toString());
    }
	
}
