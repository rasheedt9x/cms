
package com.sgdc.cms.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sgdc.cms.models.StudentGroup;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {

    public StudentGroup findByGroupName(String groupName);
}
