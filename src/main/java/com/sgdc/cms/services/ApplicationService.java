package com.sgdc.cms.services;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.ApplicationDto;
import com.sgdc.cms.exceptions.ApplicationSaveException;
import com.sgdc.cms.models.Application;
import com.sgdc.cms.models.ApplicationStatus;
import com.sgdc.cms.models.Student;
import com.sgdc.cms.repositories.ApplicationRepository;
import com.sgdc.cms.repositories.StudentRepository;

@Service
public class ApplicationService {
    private final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    private ApplicationRepository repository;

    private StudentRepository studentRepository;

    @Autowired
    public ApplicationService(ApplicationRepository repository, StudentRepository studentRepo) {
        this.repository = repository;
        this.studentRepository = studentRepo;
    }

    public String[] saveApplication(ApplicationDto dto) {
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
            throw new ApplicationSaveException("Failed to save application", e);
        }

        try {
            repository.save(application);
            return new String[] { application.getApplicationId(), application.getEmail() };
        } catch (Exception e) {
            logger.error("Error saving application: " + e.getMessage());
            throw new ApplicationSaveException("Failed to save application", e);
        }
    }

    public Long allApplicationCount() {
        return repository.count();
    }

    public Application getApplicationByApplicationId(String applicationId) {
        return repository.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

    }

    public Long getCountByStatus(ApplicationStatus status) {
        return repository.countByStatus(status);
    }

    public List<Application> getAllApplications() {
        return repository.findAll();
    }

    public List<Application> getAllApplicationsByPage(int pageNumber, int pageSize) {
        return repository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
    }

    public List<Application> getAllApplicationsByStatus(ApplicationStatus status) {
        return repository.findByStatus(status);
    }

    public Map<String, Object> updateStatus(String id, ApplicationStatus status) {
        Application application = repository.findByApplicationId(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);

        try {
            repository.save(application);
        } catch (Exception e) {
            logger.error("Error updating status: " + e.getMessage());
            throw new RuntimeException("Error updating status");
        }

        Student dto = new Student();
        dto.setName(application.getName());
        dto.setUsername(application.getUsername());
        dto.setAddress(application.getAddress());
        dto.setYearOfStudy(1);
        dto.setEmail(application.getEmail());
        dto.setGender(application.getGender());
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        dto.setDateOfBirth(application.getDateOfBirth());
        dto.setNationality(application.getNationality());
        dto.setDegreeCourse(application.getDegreeCourse());

        dto.setPrimaryPhone(application.getPrimaryPhone());
        dto.setSecondaryPhone(application.getSecondaryPhone());

        dto.setGuardianName(application.getGuardianName());
        dto.setGuardianPhone(application.getGuardianPhone());

        dto.setSscSchool(application.getSscSchool());
        dto.setSscYearOfPassing(application.getSscYearOfPassing());
        dto.setSscMarks(application.getSscMarks());

        dto.setIntermediateCollege(application.getIntermediateCollege());
        dto.setIntermediateYearOfPassing(application.getIntermediateYearOfPassing());
        dto.setIntermediateMarks(application.getIntermediateMarks());

        dto.setSecondLanguage(application.getSecondLanguage());
        dto.setCaste(application.getCaste());
        dto.setReligion(application.getReligion());

        dto.setStudentAadhaar(application.getStudentAadhaar());
        dto.setMotherAadhaar(application.getMotherAadhaar());

        try {
            studentRepository.save(dto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save Student from Application", e);
        }

        String studentId = dto.getStudentId();
        String defaultPassword = "SGDC@123";

        Map<String,Object> map = new HashMap<>();
        map.put("studentId", studentId);
        map.put("defaultPassword", defaultPassword);
        return map;
    }
}
