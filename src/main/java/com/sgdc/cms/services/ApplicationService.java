package com.sgdc.cms.services;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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


    public String[] saveApplication(ApplicationDto dto){
        Application application = new Application();
        application.setName(dto.getName());
        application.setStatus(ApplicationStatus.PENDING);
        application.setGender(dto.getGender());
        application.setNationality(dto.getNationality());
        application.setAddress(dto.getAddress());

        application.setSscSchool(dto.getSscSchool());
        application.setSscYearOfPassing(dto.getSscYearOfPassing());
        application.setSscMarks(dto.getSscMarks());

        application.setIntermediateCollege(dto.getIntermediateCollege());
        application.setIntermediateYearOfPassing(dto.getIntermediateYearOfPassing());
        application.setIntermediateMarks(dto.getIntermediateMarks());

        application.setPrimaryPhone(dto.getPrimaryPhone());
        application.setSecondaryPhone(dto.getSecondaryPhone());
        application.setGuardianName(dto.getGuardianName());
        application.setGuardianPhone(dto.getGuardianPhone());

        application.setSecondLanguage(dto.getSecondLanguage());
        application.setDegreeCourse(dto.getDegreeCourse());
        application.setReligion(dto.getReligion());
        application.setUsername(dto.getUsername());
        application.setCaste(dto.getCaste());
        application.setMotherAadhaar(dto.getMotherAadhaar());
        application.setStudentAadhaar(dto.getStudentAadhaar());


        if (repository.existsByEmail(dto.getEmail())) {
            throw new ApplicationSaveException("Email already exists", new RuntimeException());
        } else {
            application.setEmail(dto.getEmail());
        }


        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            application.setDateOfBirth(format.parse(dto.getDateOfBirth()));
              	
        } catch (Exception e) {
            logger.error("Failed to parse date: " + dto.getDateOfBirth(), e);
            throw new ApplicationSaveException("Failed to save application",e);
        }
       
        try {	
            repository.save(application);
            return new String[] {application.getApplicationId(), application.getEmail()};
        } catch (Exception e) {
            logger.error("Error saving application: "+e.getMessage());
            throw new ApplicationSaveException("Failed to save application", e);
        }      
    }

    public Application getApplicationByApplicationId(String applicationId){
        return repository.findByApplicationId(applicationId).orElseThrow(() -> new RuntimeException("Application not found"));

    }

	public List<Application> getAllApplications(){
	    return repository.findAll();
	}

	public List<Application> getAllApplicationsByPage(int pageNumber,int pageSize) {
	    return repository.findAll(PageRequest.of(pageNumber,pageSize)).toList();
	}

    public List<Application> getAllApplicationsByStatus(ApplicationStatus status) {
        return repository.findByStatus(status);
    }

	public Application updateStatus(String id, ApplicationStatus status) {
        Application application = repository.findByApplicationId(id).orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        return repository.save(application);    
	}
}
