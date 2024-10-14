package com.sgdc.cms.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sgdc.cms.models.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Application a WHERE a.email = :email")
    boolean existsByEmail(@Param("email") String email);
}
