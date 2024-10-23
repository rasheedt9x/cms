package com.sgdc.cms.controllers;



import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sgdc.cms.dto.StudentDto;
import com.sgdc.cms.dto.UpdateUserDto;
import com.sgdc.cms.models.Student;
import com.sgdc.cms.services.StudentService;
import com.sgdc.cms.utils.StorageUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * StudentController
 */
@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private StudentService studentService;  
    private StorageUtils storageUtils;

    @Autowired
    public StudentController(StudentService s){
        this.studentService = s;
    }

    @GetMapping(path = "/ping")
    public ResponseEntity<String> ping(){
        Path path = Paths.get(StorageUtils.UPLOAD_DIR);
        logger.info(path.toAbsolutePath().toString());
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



    
	@PostMapping("/get/updateProfile")
	public ResponseEntity<?> changePassOrEmail (@RequestBody UpdateUserDto dto,@RequestHeader("Authorization") String token) {
        boolean updated = studentService.changePasswordOrEmail(dto, token.substring(7));
	    return ResponseEntity.status(HttpStatus.OK).body("Password or Email Changed Successfully");
	}
	
}
