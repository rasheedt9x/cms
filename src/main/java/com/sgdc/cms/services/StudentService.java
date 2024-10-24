package com.sgdc.cms.services;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.ImageUpdateDto;
import com.sgdc.cms.dto.StudentDto;
import com.sgdc.cms.dto.UpdateUserDto;
import com.sgdc.cms.models.Department;
import com.sgdc.cms.models.Employee;
import com.sgdc.cms.models.Role;
import com.sgdc.cms.models.Student;
import com.sgdc.cms.models.StudentGroup;
import com.sgdc.cms.repositories.DepartmentRepository;
import com.sgdc.cms.repositories.EmployeeRepository;
import com.sgdc.cms.repositories.RoleRepository;
import com.sgdc.cms.repositories.StudentGroupRepository;
import com.sgdc.cms.repositories.StudentRepository;
import com.sgdc.cms.security.jwt.JwtTokenProvider;
import com.sgdc.cms.utils.StorageUtils;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private StudentGroupRepository studentGroupRepo;
    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public StudentService(StudentRepository repo, PasswordEncoder pwe, RoleRepository roleRepo,
            StudentGroupRepository sgr, DepartmentRepository deptRepo) {
        this.studentRepository = repo;
        this.passwordEncoder = pwe;
        this.roleRepository = roleRepo;
        this.studentGroupRepo = sgr;
        this.departmentRepository = deptRepo;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Student findStudentByToken(String token) {
        String username = this.jwtTokenProvider.getUsernameFromToken(token);
        Student s = studentRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Student not found"));
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
            // s.setUsername(dto.getUsername());
            // s.setPassword(passwordEncoder.encode("SGDC@123"));
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

            String depName = null;
            if (dto.getDegreeCourse().equals("BCA") || dto.getDegreeCourse().equals("BSC")) {
                depName = "Computer Science";
            } else if (dto.getDegreeCourse().equals("BCOM") || dto.getDegreeCourse().equals("BBA")) {
                depName = "Commerce";
            } else if (dto.getDegreeCourse().equals("BIOTECH") || dto.getDegreeCourse().equals("BZC")) {
                depName = "Sciences";
            } else {
                throw new RuntimeException("Error saving student -> department not found with name " + depName);
            }

            Department dept = departmentRepository.findByDepartmentName(depName);
            if (dept == null) {
                throw new RuntimeException(
                        "Error saving student -> department search -> not found with name " + depName);
            } else {
                s.setDepartment(dept);
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

    public boolean changePasswordOrEmail(UpdateUserDto dto, String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        Student e = studentRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Failed to update"));

        // pass change
        if (dto.getOldPassword() != null && dto.getNewPassword() != null) {
            if (!passwordEncoder.matches(dto.getOldPassword(), e.getPassword())) {
                throw new RuntimeException("Old password doesnt match");
            }

            // if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            // throw new RuntimeException("Passwords do not match");
            // }

            e.setPassword(passwordEncoder.encode(dto.getNewPassword()));

            try {
                studentRepository.save(e);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to update pwd in db");
            }
        }

        // mail change (if provided)
        if (dto.getEmail() != null) {
            if (dto.getOldPassword() != null && passwordEncoder.matches(dto.getOldPassword(), e.getPassword())) {
                e.setEmail(dto.getEmail());

                try {
                    studentRepository.save(e);
                } catch (Exception ex) {
                    throw new RuntimeException("Failed to update pwd in db");
                }

            } else {
                throw new RuntimeException("Error while updating email -> password doesnt match");
            }
        }

        return true;

    }

    public List<Employee> retrieveTeachersByDepartment(String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        Student s = studentRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Retrieving teachers in student dept -> couldnt find student"));
        logger.info(s.getName() + " " + "fetching teachers");
        Department d = s.getDepartment();
        logger.info(s.getDepartment().getDepartmentName() + " " + "teachers");
        List<Employee> l = employeeRepository.findAllByDepartment(d);
        logger.info("Length of l: {}", l.size());
        return l;
    }

    public ImageUpdateDto updateStudentImage(ImageUpdateDto dto, String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        Student student = studentRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Failed to update image -> stud not found"));

        try {
            if (dto.getImageBase64() != null) {
                byte[] image = Base64.getDecoder().decode(dto.getImageBase64());
                String imagePath = StorageUtils.saveImageToStorage(image, "students", student.getStudentId() + ".jpg");
                student.setImage(imagePath);
                logger.info("Saving image as " + student.getImage());
                student = studentRepository.save(student);
            } else {
                student.setImage(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failure updation of image");
        }

        return dto;
    }

    // public ImageUpdateDto getImage(String token) {
    //     String username = jwtTokenProvider.getUsernameFromToken(token);
    //     Student student = studentRepository.findByUsername(username).orElseThrow(
    //             () -> new RuntimeException("Failed to fetch image -> stud not found"));
    //     logger.info("Fetching image for " + student.getName());
    //     ImageUpdateDto dto = new ImageUpdateDto();

    //     logger.info("Student Image is " + student.getImage());
    //     if (student.getImage() != null) {
    //         byte[] imageBytes = StorageUtils.getImageBytes(student.getImage());
    //         String b64 = Base64.getEncoder().encodeToString(imageBytes);
    //         logger.info(b64);
    //         dto.setImageBase64(b64);
    //     } else {
    //         logger.info("Student image is null");
    //     }
    //     return dto;
    // }

public ImageUpdateDto getImage(String token) {
    logger.info("Received request to fetch image for token: " + token);
    try {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        logger.info("Username from token: " + username);

        Student student = studentRepository.findByUsername(username).orElseThrow(
            () -> new RuntimeException("Failed to fetch image -> student not found")
        );

        logger.info("Fetching image for " + student.getName());

        ImageUpdateDto dto = new ImageUpdateDto();

        if (student.getImage() != null) {
            byte[] imageBytes = StorageUtils.getImageBytes(student.getImage());
            String b64 = Base64.getEncoder().encodeToString(imageBytes);
            logger.info("Image fetched and encoded successfully");
            dto.setImageBase64(b64);
        } else {
            logger.warn("Student image is null");
        }

        return dto;

    } catch (Exception e) {
        logger.error("Error occurred in getImage: ", e);
        throw new RuntimeException("Failed to fetch image", e);
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
