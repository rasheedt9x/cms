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

/**
 * EmployeeService
 */
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
            if (employeeRepository.existsByUsername("admin")) {
                return;
            }
            Employee e = new Employee();
            e.setName("ADMIN");
            e.setEmail("admin@example.com");
            e.setUsername("admin");
            e.setPassword(passwordEncoder.encode("1234"));

            Role role = roleRepository.findByRoleName("ADMISSION_MANAGER");
            if (role != null) {
                e.addRoles(role);
            } else {
                Role tRole = new Role("ADMISSION_MANAGER");
                roleRepository.save(tRole);
                e.addRoles(tRole);
            }

            logger.info("Init Admins");
            Department dept = departmentRepository.findByDepartmentName("MANAGEMENT");
            if (dept != null) {
                e.setDepartment(dept);
            } else {
                Department d = new Department();
                d.setDepartmentName("MANAGEMENT");
                departmentRepository.save(d);
                e.setDepartment(d);
            }

            employeeRepository.save(e);
        } catch (Exception ex) {
            logger.error("Error during PostConstruct initialization: ", ex);
        }
    }

    // @PostConstruct
    // public void initAdmins() {

    // Employee e = new Employee();
    // e.setName("ADMIN");
    // e.setEmail("admin@example.com");
    // e.setUsername("admin");
    // e.setPassword(passwordEncoder.encode("1234"));

    // Role role = roleRepository.findByRoleName("EMPLOYEE");
    // if (role != null) {
    // e.addRoles(role);
    // } else {
    // Role tRole = new Role("EMPLOYEE");
    // roleRepository.save(tRole);
    // e.addRoles(tRole);
    // }

    // logger.info("Init Admins");
    // Department dept = departmentRepository.findByDepartmentName("ADMIN");
    // if (dept != null) {
    // e.setDepartment(dept);
    // } else {
    // Department d = new Department();
    // d.setDepartmentName("ADMIN");
    // departmentRepository.save(d);
    // }

    // employeeRepository.save(e);
    // }

    public Employee saveEmployee(EmployeeDto dto) {
        try {
            Employee e = new Employee();
            e.setName(dto.getName());
            e.setEmail(dto.getEmail());
            e.setUsername(dto.getUsername());
            e.setPassword(passwordEncoder.encode("1234"));

            Role role = roleRepository.findByRoleName("EMPLOYEE");
            if (role != null) {
                e.addRoles(role);
            } else {
                Role tRole = new Role("EMPLOYEE");
                roleRepository.save(tRole);
                e.addRoles(tRole);
            }

            logger.info(dto.getDepartmentName());
            Department dept = departmentRepository.findByDepartmentName(dto.getDepartmentName());
            if (dept != null) {
                e.setDepartment(dept);
            } else {
                throw new RuntimeException("Department not found");
            }

            return employeeRepository.save(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldnt save employee", e);
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
