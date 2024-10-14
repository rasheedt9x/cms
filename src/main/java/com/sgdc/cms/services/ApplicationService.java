package com.sgdc.cms.services;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.ApplicationDto;
import com.sgdc.cms.exceptions.ApplicationSaveException;
import com.sgdc.cms.models.Application;
import com.sgdc.cms.models.ApplicationStatus;
import com.sgdc.cms.repositories.ApplicationRepository;


@Service
public class ApplicationService {    
	private final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    private ApplicationRepository repository;

    @Autowired
    public ApplicationService(ApplicationRepository repository){
        this.repository = repository;
    }


    public Long saveApplication(ApplicationDto dto){
        Application application = new Application();
        application.setName(dto.getName());
        application.setEmail(dto.getEmail());
        application.setStatus(ApplicationStatus.PENDING);
        application.setGender(dto.getGender());
        application.setNationality(dto.getNationality());
        application.setAddress(dto.getAddress());
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            application.setDateOfBirth(format.parse(dto.getDateOfBirth()));
              	
        } catch (Exception e) {
            logger.error("Failed to parse date: " + dto.getDateOfBirth(), e);
            throw new ApplicationSaveException("Failed to save application",e);
        }
       
        try {	
            repository.save(application);
            return application.getId();
        } catch (Exception e) {
            logger.error("Error saving application: "+e.getMessage());
            throw new ApplicationSaveException("Failed to save application", e);
        }      
    }

	public List<Application> getAllApplications(){
	    return repository.findAll();
	}

	public Application updateStatus(Long id, ApplicationStatus status) {
        Application application = repository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        return repository.save(application);    
	}
}
