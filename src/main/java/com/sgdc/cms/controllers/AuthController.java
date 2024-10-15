package com.sgdc.cms.controllers;

import javax.lang.model.element.ModuleElement.UsesDirective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgdc.cms.dto.AuthRequest;
import com.sgdc.cms.dto.AuthResponse;
import com.sgdc.cms.security.jwt.JwtTokenProvider;
import com.sgdc.cms.services.EmployeeService;
import com.sgdc.cms.services.StudentService;

import ch.qos.logback.classic.net.SocketAppender;

/**
 * AuthController
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private StudentService studentService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String id = new String(username);
        System.out.println("Got username: " + username);
        try {
            username = studentService.findStudentByStudentId(id);
            if(username == null) {
                username = employeeService.findEmployeeByEmployeeId(id);
                System.out.println("Emp: " + username);
            }
            if (username == null) {
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,authRequest.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String token = jwtTokenProvider.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }

}