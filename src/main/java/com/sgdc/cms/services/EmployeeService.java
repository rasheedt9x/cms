package com.sgdc.cms.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.EmployeeDto;
import com.sgdc.cms.models.Department;
import com.sgdc.cms.models.Employee;
import com.sgdc.cms.models.Role;
import com.sgdc.cms.repositories.DepartmentRepository;
import com.sgdc.cms.repositories.EmployeeRepository;
import com.sgdc.cms.repositories.RoleRepository;
import com.sgdc.cms.security.jwt.JwtTokenProvider;

import jakarta.annotation.PostConstruct;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private DepartmentRepository departmentRepository;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public EmployeeService(EmployeeRepository empRepo, PasswordEncoder pwe, RoleRepository roleRepo,
                           DepartmentRepository dRepo) {
        this.employeeRepository = empRepo;
        this.passwordEncoder = pwe;
        this.roleRepository = roleRepo;
        this.departmentRepository = dRepo;
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
            createAdminIfNotExists("adm_admin", "ADMISSION_ADMIN", "adm_admin@example.com", new String[]{"ADMISSION_MANAGER","EMPLOYEE"});
            createAdminIfNotExists("admin", "ADMIN", "admin@example.com", new String[]{"ADMIN","EMPLOYEE"});
    	    createAdminIfNotExists("libr123","LIBRARIAN","librarian@example.com",new String[]{"LIBRARIAN","EMPLOYEE"});
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

            LocalDate dob = LocalDate.parse(dto.getDateOfBirth(),dtf);
            e.setDateOfBirth(dob);

            
            // LocalDate doj = LocalDate.parse(dto.(),dtf);
            e.setDateOfJoining(LocalDate.now());
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
            () -> new RuntimeException("Couldnt find employee with provided auth")
        );
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

        for(Role r : roles) {
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

    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
       employeeDto.setName(employee.getName());                     
        employeeDto.setEmail(employee.getEmail());                   
        employeeDto.setUsername(employee.getUsername());             
        employeeDto.setDepartmentName(employee.getDepartment() != null ? employee.getDepartment().getDepartmentName() : null);
        
        employeeDto.setNationality(employee.getNationality());       
        employeeDto.setGender(employee.getGender());                 
        employeeDto.setAddress(employee.getAddress());               
        employeeDto.setDateOfBirth(employee.getDateOfBirth() != null? employee.getDateOfBirth().toString(): null);      
        
        
        employeeDto.setDateOfJoining(employee.getDateOfJoining() != null? employee.getDateOfJoining().toString(): null);   
        employeeDto.setDateOfLeaving(employee.getDateOfLeaving() != null? employee.getDateOfLeaving().toString(): null);   

        employeeDto.setEmployeeId(employee.getEmployeeId());         
        return employeeDto;
    }

}

