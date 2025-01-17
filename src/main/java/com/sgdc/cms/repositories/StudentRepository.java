
package com.sgdc.cms.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sgdc.cms.models.Department;
import com.sgdc.cms.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    public Optional<Student> findByUsername(String username);

    public Student findByStudentId(String studentId);

    public List<Student> findAllByDepartment(Department department);
  
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s WHERE s.username = :username")
    boolean existsByUsername(@Param("username") String username);

    
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s WHERE s.studentId = :studentId")
    boolean existsByStudentId(@Param("studentId") String studentId);

}
