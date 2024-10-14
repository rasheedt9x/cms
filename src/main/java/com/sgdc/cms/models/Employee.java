package com.sgdc.cms.models;

import com.sgdc.cms.annotations.CustomEmployeeID;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "employees")
public class Employee extends User{

	@CustomEmployeeID
	private String employeeId;
	
	@ManyToOne
    @JoinColumn(name = "group_id", nullable=false)    
    private Department department;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

       
    public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

}
