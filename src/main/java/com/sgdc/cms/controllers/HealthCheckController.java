package com.sgdc.cms.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthCheckController
 */
@RestController
@RequestMapping("/api/v1/stat")
public class HealthCheckController {

    @GetMapping("/health")
	public ResponseEntity<?> health(){
	    return ResponseEntity.ok().build();
	}

    @GetMapping("/ping")
	public ResponseEntity<?> ping(){
	    return ResponseEntity.status(HttpStatus.OK).body("Pong");
	}
}
