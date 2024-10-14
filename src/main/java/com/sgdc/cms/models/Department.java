package com.sgdc.cms.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Department
 */
@Entity
@Table(name = "departments")
public class Department {

	
	@Id
	@Column(name = "department_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "department_name")
    private String departmentName;


    public Department(){}
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }   

    public String getDepartmentName() {
        return this.departmentName;
    }
}
