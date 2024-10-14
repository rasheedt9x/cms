
package com.sgdc.cms.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sgdc.cms.models.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    public Department findByDepartmentName(String departmentName);
}
