package com.sgdc.cms.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgdc.cms.models.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
