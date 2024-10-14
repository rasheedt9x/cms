package com.sgdc.cms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgdc.cms.models.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{

    public Role findByRoleName(String name);
}
