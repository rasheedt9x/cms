package com.sgdc.cms.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.EmployeeDto;
import com.sgdc.cms.dto.ImageUpdateDto;
import com.sgdc.cms.dto.UpdateUserDto;
import com.sgdc.cms.models.Department;
import com.sgdc.cms.models.Employee;
import com.sgdc.cms.models.Role;
import com.sgdc.cms.models.Student;
import com.sgdc.cms.repositories.DepartmentRepository;
import com.sgdc.cms.repositories.EmployeeRepository;
import com.sgdc.cms.repositories.RoleRepository;
import com.sgdc.cms.repositories.StudentRepository;
import com.sgdc.cms.security.jwt.JwtTokenProvider;
import com.sgdc.cms.utils.StorageUtils;

import jakarta.annotation.PostConstruct;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private DepartmentRepository departmentRepository;
    private JwtTokenProvider jwtTokenProvider;
    private StudentRepository studentRepository;

    @Autowired
    public EmployeeService(EmployeeRepository empRepo, PasswordEncoder pwe, RoleRepository roleRepo,
            DepartmentRepository dRepo) {
        this.employeeRepository = empRepo;
        this.passwordEncoder = pwe;
        this.roleRepository = roleRepo;
        this.departmentRepository = dRepo;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public JwtTokenProvider getJwtTokenProvider() {
        return jwtTokenProvider;
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostConstruct
    public void initAdmins() {
        try {
            createAdminIfNotExists("adm_admin", "ADMISSION_ADMIN", "adm_admin@example.com",
                    new String[] { "ADMISSION_MANAGER", "EMPLOYEE" });
            createAdminIfNotExists("admin", "ADMIN", "admin@example.com", new String[] { "ADMIN", "EMPLOYEE" });
            createAdminIfNotExists("libr123", "LIBRARIAN", "librarian@example.com",
                    new String[] { "LIBRARIAN", "EMPLOYEE" });
        } catch (Exception ex) {
            logger.error("Error during PostConstruct initialization: ", ex);
        }
    }

    public EmployeeDto saveEmployee(EmployeeDto dto) {
        try {
            Employee e = new Employee();
            e.setName(dto.getName());
            e.setEmail(dto.getEmail());
            e.setUsername(dto.getUsername());
            e.setPassword(passwordEncoder.encode("SGDC@123"));

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate dob = LocalDate.parse(dto.getDateOfBirth(), dtf);
            e.setDateOfBirth(dob);

            // LocalDate doj = LocalDate.parse(dto.(),dtf);
            e.setDateOfApproval(LocalDate.now());
            e.setDateOfJoining(LocalDate.now().plusDays(7));
            e.setDateOfLeaving(null);
            e.setEmployed(true);
            e.setGender(dto.getGender());
            e.setNationality(dto.getNationality());

            Role role = roleRepository.findByRoleName("EMPLOYEE");
            if (role != null) {
                e.addRole(role);
            } else {
                Role tRole = new Role("EMPLOYEE");
                roleRepository.save(tRole);
                e.addRole(tRole);
            }

            logger.info("Department name: {}", dto.getDepartmentName());
            Department dept = departmentRepository.findByDepartmentName(dto.getDepartmentName());
            if (dept != null) {
                e.setDepartment(dept);
            } else {
                throw new RuntimeException("Department not found");
            }

            e = employeeRepository.save(e);
            return convertToDto(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't save employee", e);
        }
    }

    public EmployeeDto retrieveEmployeeByToken(String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        Employee e = employeeRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Couldnt find employee with provided auth"));
        return convertToDto(e);
    }

    public String findEmployeeByEmployeeId(String employeeId) {
        Employee e = employeeRepository.findByEmployeeId(employeeId);
        if (e != null) {
            return e.getUsername();
        } else {
            return null;
        }
    }

    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName(employee.getName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setUsername(employee.getUsername());
        employeeDto.setDepartmentName(
                employee.getDepartment() != null ? employee.getDepartment().getDepartmentName() : null);

        employeeDto.setNationality(employee.getNationality());
        employeeDto.setGender(employee.getGender());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setDateOfBirth(employee.getDateOfBirth() != null ? employee.getDateOfBirth().toString() : null);

        employeeDto.setDateOfApproval(
                employee.getDateOfApproval() != null ? employee.getDateOfApproval().toString() : null);
        employeeDto
                .setDateOfJoining(employee.getDateOfJoining() != null ? employee.getDateOfJoining().toString() : null);
        employeeDto
                .setDateOfLeaving(employee.getDateOfLeaving() != null ? employee.getDateOfLeaving().toString() : null);
        employeeDto.setEmployed(employee.isEmployed());

        employeeDto.setEmployeeId(employee.getEmployeeId());
        return employeeDto;
    }

    public boolean changePasswordOrEmail(UpdateUserDto dto, String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        Employee e = employeeRepository.findByUsername(username).orElseThrow(
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
                employeeRepository.save(e);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to update pwd in db");
            }
        }

        // mail change (if provided)
        if (dto.getEmail() != null) {
            if (dto.getOldPassword() != null && passwordEncoder.matches(dto.getOldPassword(), e.getPassword())) {
                e.setEmail(dto.getEmail());

                try {
                    employeeRepository.save(e);
                } catch (Exception ex) {
                    throw new RuntimeException("Failed to update pwd in db");
                }

            } else {
                throw new RuntimeException("Error while updating email -> password doesnt match");
            }
        }

        return true;
    }

    private void createAdminIfNotExists(String username, String name, String email, String[] roleNames) {
        if (employeeRepository.existsByUsername(username)) {
            return; // Admin already exists
        }

        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employee.setUsername(username);
        employee.setPassword(passwordEncoder.encode("1234"));

        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByRoleName(roleName);
            if (role == null) {
                role = new Role(roleName);
                roleRepository.save(role);
            }
            roles.add(role);
        }

        for (Role r : roles) {
            r.setRolename(r.getRolename());
            employee.addRole(r);
        }
        logger.info("Creating admin user: {}", username);
        Department dept = departmentRepository.findByDepartmentName("MANAGEMENT");
        if (dept == null) {
            dept = new Department();
            dept.setDepartmentName("MANAGEMENT");
            departmentRepository.save(dept);
        }
        employee.setDepartment(dept);

        employeeRepository.save(employee);
        logger.info("Admin user created: {}", username);
    }

    public List<Student> retriveStudentsinDepartment(String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        Employee e = employeeRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Retrieving Students by dept -> emp not found"));
        Department d = e.getDepartment();
        List<Student> l = studentRepository.findAllByDepartment(d);
        return l;
    }

    public ImageUpdateDto getImage(String token) {

        logger.info("Received request to fetch image for token: " + token);
        try {
            String username = jwtTokenProvider.getUsernameFromToken(token);
            logger.info("Username from token: " + username);

            Employee employee = employeeRepository.findByUsername(username).orElseThrow(
                    () -> new RuntimeException("Failed to fetch image -> employee not found"));

            logger.info("Fetching image for " + employee.getName());

            ImageUpdateDto dto = new ImageUpdateDto();

            if (employee.getImage() != null) {
                byte[] imageBytes = StorageUtils.getImageBytes(employee.getImage());
                String b64 = Base64.getEncoder().encodeToString(imageBytes);
                logger.info("Image fetched and encoded successfully");
                dto.setImageBase64(b64);
            } else {
                logger.warn("Employee image is null");
            }

            return dto;

        } catch (Exception e) {
            logger.error("Error occurred in getImage: ", e);
            throw new RuntimeException("Failed to fetch image", e);
        }

    }

    public ImageUpdateDto updateEmployeeImage(ImageUpdateDto dto, String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        Employee e = employeeRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Failed to update image -> emp not found"));

        try {
            if (dto.getImageBase64() != null) {
                byte[] image = Base64.getDecoder().decode(dto.getImageBase64());
                String imagePath = StorageUtils.saveImageToStorage(image, "employees", e.getEmployeeId() + ".jpg");
                e.setImage(imagePath);
                logger.info("Saving image as " + e.getImage());
                e = employeeRepository.save(e);
            } else {
                e.setImage(null);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failure updation of image");
        }

        return dto;
    }

}
