package com.sgdc.cms.controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sgdc.cms.dto.StudentDto;
import com.sgdc.cms.services.StudentService;

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

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getStudentById(@RequestParam("id") String studentId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/new")
    public ResponseEntity<?> addStudent(@RequestBody StudentDto dto){
        Object obj = studentService.saveStudent(dto);
        return ResponseEntity.ok(obj.toString());
    }
	
}
