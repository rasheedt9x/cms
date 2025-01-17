package com.sgdc.cms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.sgdc.cms.dto.ApplicationDto;
import com.sgdc.cms.models.Application;
import com.sgdc.cms.models.ApplicationStatus;
import com.sgdc.cms.services.ApplicationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
@RequestMapping("/api/v1/applications")
public class ApplicationController {

	private final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	private final ApplicationService applicationService;

	@Autowired
	public ApplicationController(ApplicationService service) {
		this.applicationService = service;		
	}

	
	@PostMapping("/new")
	public ResponseEntity<?> apply(@RequestBody ApplicationDto dto) {
		String[] obj = applicationService.saveApplication(dto);
		Map<String, String> map = new HashMap<>();
		map.put("application_id", obj[0]);
		map.put("email",obj[1]);
		ResponseEntity<?> resp = ResponseEntity
					.status(HttpStatus.CREATED).body(map);
					//.body("{\"application_id\":\" "+obj[0] + "\", \"email\" : \""+ obj[1] + "\"}");
					
	    return resp;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> applicationByApplicationId(@PathVariable("id") String applicationId){
		Application application = applicationService.getApplicationByApplicationId(applicationId);
		return ResponseEntity.ok(application);
	}



	@GetMapping("/all")
	public ResponseEntity<List<Application>> allApplications(){
		List<Application> list = applicationService.getAllApplications();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/all/{page}")
	public ResponseEntity<List<Application>> getApplicationsByPage(@PathVariable("page") int page) {
		page -= 1;
		List<Application> list = applicationService.getAllApplicationsByPage(page, 10);
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/count/{status}")
	public ResponseEntity<?> findApplicationCountByStatus(@PathVariable("status") String status) {
		ApplicationStatus appStatus = ApplicationStatus.valueOf(status);
		Long count = applicationService.getCountByStatus(appStatus);
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		map.put("status",status.toString());
		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> getAllApplicationCount() {
		Long count = applicationService.allApplicationCount();
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/all/status/{status}")
	public ResponseEntity<List<Application>> getApplicationsByStatus(@PathVariable("status") String status) {
		ApplicationStatus appStatus = ApplicationStatus.valueOf(status);
		List<Application> list = applicationService.getAllApplicationsByStatus(appStatus);
		return ResponseEntity.ok(list);
	}


//	@PostMapping("/{id}/status")
//	public ResponseEntity<Application> updateApplicationStatus(@PathVariable("id") String applicationId, @RequestParam("status") ApplicationStatus status){
//		Application application = applicationService.updateStatus(applicationId, status);
//		return ResponseEntity.ok(application);
//	}

	@PostMapping(value = "/{id}/status", consumes = "application/json")
	public ResponseEntity<?> updateApplicationStatus(
			@PathVariable("id") String applicationId,
			@RequestBody Map<String, String> requestBody
	) {
		ApplicationStatus status = ApplicationStatus.valueOf(requestBody.get("status"));
		Map<String, Object> statusResponse = applicationService.updateStatus(applicationId, status);
		return ResponseEntity.ok(statusResponse);
	}



}
