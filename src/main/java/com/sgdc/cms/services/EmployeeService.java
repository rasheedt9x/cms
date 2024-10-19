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

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeService(EmployeeRepository empRepo, PasswordEncoder pwe, RoleRepository roleRepo,
                           DepartmentRepository dRepo) {
        this.employeeRepository = empRepo;
        this.passwordEncoder = pwe;
        this.roleRepository = roleRepo;
        this.departmentRepository = dRepo;
    }

    @PostConstruct
    public void initAdmins() {
        try {
            createAdminIfNotExists("adm_admin", "ADMISSION_ADMIN", "adm_admin@example.com", new String[]{"ADMISSION_MANAGER"});
            createAdminIfNotExists("admin", "ADMIN", "admin@example.com", new String[]{"ADMIN"});
    	    createAdminIfNotExists("libr123","LIBRARIAN","librarian@example.com",new String[]{"LIBRARIAN"});
        } catch (Exception ex) {
            logger.error("Error during PostConstruct initialization: ", ex);
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

    public Employee saveEmployee(EmployeeDto dto) {
        try {
            Employee e = new Employee();
            e.setName(dto.getName());
            e.setEmail(dto.getEmail());
            e.setUsername(dto.getUsername());
            e.setPassword(passwordEncoder.encode("1234"));

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

            return employeeRepository.save(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't save employee", e);
        }
    }

    public String findEmployeeByEmployeeId(String employeeId) {
        Employee e = employeeRepository.findByEmployeeId(employeeId);
        if (e != null) {
            return e.getUsername();
        } else {
            return null;
        }
    }
}

