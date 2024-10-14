package com.sgdc.cms.models;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    public Role(){}
    public Role(String rolename) {
        this.roleName = rolename;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setRolename(String rolename) {
        this.roleName = rolename;
    }   

    public String getRolename() {
        return this.roleName;
    }

}
 
