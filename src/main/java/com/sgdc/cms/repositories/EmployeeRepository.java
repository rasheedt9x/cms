package com.sgdc.cms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sgdc.cms.models.Department;
import com.sgdc.cms.models.Employee;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {
 
    public Optional<Employee> findByUsername(String username);

    public Employee findByEmployeeId(String employeeId);

    public List<Employee> findAllByDepartment(Department department);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.username = :username")
    boolean existsByUsername(String username);       
    
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.employeeId = :employeeId")
    boolean existsByEmployeeId(@Param("employeeId") String employeeId);       
}
