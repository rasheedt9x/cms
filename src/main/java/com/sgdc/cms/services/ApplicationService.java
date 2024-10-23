package com.sgdc.cms.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sgdc.cms.models.StudentGroup;
import com.sgdc.cms.repositories.StudentGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.ApplicationDto;
import com.sgdc.cms.exceptions.ApplicationSaveException;
import com.sgdc.cms.models.Application;
import com.sgdc.cms.models.ApplicationStatus;
import com.sgdc.cms.models.Department;
import com.sgdc.cms.models.Role;
import com.sgdc.cms.models.Student;
import com.sgdc.cms.repositories.ApplicationRepository;
import com.sgdc.cms.repositories.DepartmentRepository;
import com.sgdc.cms.repositories.RoleRepository;
import com.sgdc.cms.repositories.StudentRepository;

@Service
public class ApplicationService {
    private final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    private ApplicationRepository repository;

    private StudentRepository studentRepository;

    private StudentGroupRepository studentGroupRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private DepartmentRepository departmentRepository;

    @Autowired
    public ApplicationService(ApplicationRepository repository, StudentRepository studentRepo,
            StudentGroupRepository sgr, PasswordEncoder pwe, RoleRepository roleRepository, DepartmentRepository deptRepo) {
        this.repository = repository;
        this.studentRepository = studentRepo;
        this.studentGroupRepository = sgr;
        this.passwordEncoder = pwe;
        this.roleRepository = roleRepository;
        this.departmentRepository = deptRepo;
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

    // public Map<String, Object> updateStatus(String id, ApplicationStatus status)
    // {
    // Application application = repository.findByApplicationId(id)
    // .orElseThrow(() -> new RuntimeException("Application not found"));
    // application.setStatus(status);

    // try {
    // application = repository.save(application);

    // logger.info(application.getDegreeCourse());
    // } catch (Exception e) {
    // logger.error("Error updating status: " + e.getMessage());
    // throw new RuntimeException("Error updating status");
    // }

    // Student dto = new Student();
    // dto.setName(application.getName());
    // dto.setUsername(application.getUsername());
    // dto.setAddress(application.getAddress());
    // dto.setYearOfStudy(1);
    // dto.setEmail(application.getEmail());

    // logger.info("Group: " + dto.getGroup());

    // StudentGroup group =
    // studentGroupRepository.findByGroupName(application.getDegreeCourse());
    // if(group == null) {
    // logger.info("null bro");
    // }
    // logger.info("Group: " + group.getGroupname());
    // dto.setGroup(group);
    // dto.setGender(application.getGender());
    // SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    // dto.setDateOfBirth(application.getDateOfBirth());
    // dto.setNationality(application.getNationality());
    // dto.setDegreeCourse(application.getDegreeCourse());

    // dto.setPrimaryPhone(application.getPrimaryPhone());
    // dto.setSecondaryPhone(application.getSecondaryPhone());

    // dto.setGuardianName(application.getGuardianName());
    // dto.setGuardianPhone(application.getGuardianPhone());

    // dto.setSscSchool(application.getSscSchool());
    // dto.setSscYearOfPassing(application.getSscYearOfPassing());
    // dto.setSscMarks(application.getSscMarks());

    // dto.setIntermediateCollege(application.getIntermediateCollege());
    // dto.setIntermediateYearOfPassing(application.getIntermediateYearOfPassing());
    // dto.setIntermediateMarks(application.getIntermediateMarks());

    // dto.setSecondLanguage(application.getSecondLanguage());
    // dto.setCaste(application.getCaste());
    // dto.setReligion(application.getReligion());

    // dto.setStudentAadhaar(application.getStudentAadhaar());
    // dto.setMotherAadhaar(application.getMotherAadhaar());

    // try {
    // studentRepository.save(dto);
    // } catch (Exception e) {
    // e.printStackTrace();
    // throw new RuntimeException("Failed to save Student from Application", e);
    // }

    // String studentId = dto.getStudentId();
    // String defaultPassword = "SGDC@123";

    // Map<String,Object> map = new HashMap<>();
    // map.put("studentId", studentId);
    // map.put("defaultPassword", defaultPassword);
    // return map;
    // }

    public Map<String, Object> updateStatus(String id, ApplicationStatus status) {
        Application application = repository.findByApplicationId(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        Map<String, Object> map = new HashMap<>();
        try {
            application = repository.save(application);
            if (status == ApplicationStatus.REJECTED) {
                map.put("status", "REJECTED");
                return map;
            }
            // logger.info("Application saved with degree course: " +
            // application.getDegreeCourse());
        } catch (Exception e) {
            logger.error("Error updating application status", e);
            throw new RuntimeException("Error updating status", e);
        }

        // Creating Student DTO from Application
        Student dto = new Student();
        logger.info("Applicant name: "+ application.getName());
        dto.setName(application.getName());
        logger.info("Student Name: "+dto.getName());
        dto.setUsername(application.getUsername());
        dto.setAddress(application.getAddress());
        dto.setYearOfStudy(1);
        dto.setEmail(application.getEmail());
        dto.setPassword(passwordEncoder.encode("SGDC@123"));

        logger.info("Creating student DTO for: " + dto.getName());

        StudentGroup group = studentGroupRepository.findByGroupName(application.getDegreeCourse());
        if (group == null) {
            logger.warn("Student group not found for degree course: " + application.getDegreeCourse());
        } else {
            dto.setGroup(group);
            logger.info("Group found: " + group.getGroupname());
        }

        String depName = null;
        if (application.getDegreeCourse().equals("BCA") || application.getDegreeCourse().equals("BSC"))  {
        	depName = "Computer Science";
        } else if (  application.getDegreeCourse().equals("BCOM") || application.getDegreeCourse().equals("BBA") ) {
        	depName = "Commerce";
        } else if (    application.getDegreeCourse().equals("BIOTECH") || application.getDegreeCourse().equals("BZC")   ) {
        	depName = "Sciences";
        } else {
            throw new RuntimeException("Error approving student -> department not found");
        }

        Department dept = departmentRepository.findByDepartmentName(depName);
        if (dept == null) {
        	throw new RuntimeException("Error approving student -> department search -> not found");
        } else {
            dto.setDepartment(dept);
        }

        dto.setGender(application.getGender());
        
      
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

        Role role = roleRepository.findByRoleName("STUDENT");
        if (role != null) {
            dto.addRole(role);
        } else {
            Role tRole = new Role("STUDENT");
            roleRepository.save(tRole);
            dto.addRole(tRole);
        }

        
        
        try {
            studentRepository.save(dto);
            logger.info("Student saved with ID: " + dto.getStudentId());
        } catch (Exception e) {
            application.setStatus(ApplicationStatus.valueOf("PENDING"));
            repository.save(application);
            logger.error("Failed to save Student from Application", e); // Log the complete stack trace
            throw new RuntimeException("Failed to save Student from Application", e);
        }

        String studentId = dto.getStudentId();
        String defaultPassword = "SGDC@123";

        map.put("studentId", studentId);
        map.put("defaultPassword", defaultPassword);
        return map;
    }

}
