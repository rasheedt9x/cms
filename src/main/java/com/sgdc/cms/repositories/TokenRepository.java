package com.sgdc.cms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgdc.cms.models.Token;
@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

     
}
