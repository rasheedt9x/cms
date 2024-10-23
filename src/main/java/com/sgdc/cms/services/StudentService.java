package com.sgdc.cms.services;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.StudentDto;
import com.sgdc.cms.models.Role;
import com.sgdc.cms.models.Student;
import com.sgdc.cms.models.StudentGroup;
import com.sgdc.cms.repositories.RoleRepository;
import com.sgdc.cms.repositories.StudentGroupRepository;
import com.sgdc.cms.repositories.StudentRepository;
import com.sgdc.cms.security.jwt.JwtTokenProvider;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private StudentGroupRepository studentGroupRepo;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public StudentService(StudentRepository repo, PasswordEncoder pwe, RoleRepository roleRepo,
            StudentGroupRepository sgr) {
        this.studentRepository = repo;
        this.passwordEncoder = pwe;
        this.roleRepository = roleRepo;
        this.studentGroupRepo = sgr;
    }
    
    public Student findStudentByToken(String token) {
        String username = this.jwtTokenProvider.getUsernameFromToken(token);
        Student s = studentRepository.findByUsername(username).orElseThrow(
            () -> new RuntimeException("Student not found")
        );
        return s;
    }

    public String findStudentUsernameByStudentId(String studentId) {
        Student s = studentRepository.findByStudentId(studentId);
        if (s != null) {
            return s.getUsername();
        } else {
            return null;
        }
    }

    public Student saveStudent(StudentDto dto) {
        try {
            Student s = new Student();
            s.setName(dto.getName());
            s.setEmail(dto.getEmail());
            // s.setUsername(dto.getUsername());           s.setPassword(passwordEncoder.encode("SGDC@123"));
            s.setYearOfStudy(dto.getYearOfStudy());
            s.setCaste(dto.getCaste());
            s.setEnabled(true);

            s.setPassword(passwordEncoder.encode("SGDC@123"));

            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            s.setDateOfBirth(fmt.parse(dto.getDateOfBirth()));

            s.setSscSchool(dto.getSscSchool());
            s.setSscYearOfPassing(dto.getSscYearOfPassing());
            s.setSscMarks(dto.getSscMarks());

            s.setIntermediateCollege(dto.getIntermediateCollege());
            s.setIntermediateYearOfPassing(dto.getIntermediateYearOfPassing());
            s.setIntermediateMarks(dto.getIntermediateMarks());

            s.setGuardianName(dto.getGuardianName());
            s.setGuardianPhone(dto.getGuardianPhone());

            s.setMotherAadhaar(dto.getMotherAadhaar());
            s.setStudentAadhaar(dto.getStudentAadhaar());

            // logger.info("Student pass " + dto.getPassword());

            StudentGroup group = studentGroupRepo.findByGroupName(dto.getGroup());
            if (group != null) {
                s.setGroup(group);
            } else {
                logger.error("No Group found with name: ", dto.getGroup());
                throw new RuntimeException("No Group with name: " + dto.getGroup());
            }

            Role role = roleRepository.findByRoleName("STUDENT");
            if (role != null) {
                s.addRole(role);
            } else {
                Role tRole = new Role("STUDENT");
                roleRepository.save(tRole);
                s.addRole(tRole);
            }

            if (studentRepository.existsByUsername(dto.getUsername())) {
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

    public JwtTokenProvider getJwtTokenProvider() {
        return jwtTokenProvider;
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

}
