package com.sgdc.cms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.StudentDto;
//import com.sgdc.cms.models.Group;
import com.sgdc.cms.models.Role;
import com.sgdc.cms.models.Student;
import com.sgdc.cms.models.StudentGroup;
import com.sgdc.cms.repositories.RoleRepository;
import com.sgdc.cms.repositories.StudentGroupRepository;
import com.sgdc.cms.repositories.StudentRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class StudentService {

	private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private StudentGroupRepository studentGroupRepo;

    @Autowired
    public StudentService(StudentRepository repo, PasswordEncoder pwe, RoleRepository roleRepo, StudentGroupRepository sgr) {
        this.studentRepository = repo;
        this.passwordEncoder = pwe;
        this.roleRepository = roleRepo;
        this.studentGroupRepo = sgr;
    }


	public Student saveStudent(StudentDto dto){
	    try{
	    Student s = new Student();
	    s.setName(dto.getName());
	    s.setEmail(dto.getEmail());
	    // s.setUsername(dto.getUsername());
	    s.setPassword(passwordEncoder.encode(dto.getPassword()));
	    s.setYearOfStudy(dto.getYearOfStudy());

        // logger.info("Student pass " + dto.getPassword());
        
        StudentGroup group = studentGroupRepo.findByGroupName(dto.getGroup());
        if(group != null) {
            s.setGroup(group);
        } else {
            logger.error("No Group found with name: ", dto.getGroup());
            throw new RuntimeException("No Group with name: "+dto.getGroup());
        }

        	    
        Role role = roleRepository.findByRoleName("STUDENT");
        if (role != null) {
        	s.addRoles(role);
        } else {
            Role tRole = new Role("STUDENT");
            roleRepository.save(tRole);
            s.addRoles(tRole);
        }

        if(studentRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username exists");
        } else {
            s.setUsername(dto.getUsername());
        }
	    
	    return studentRepository.save(s);
	} catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
	}
	}
}
