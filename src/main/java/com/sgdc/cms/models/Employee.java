package com.sgdc.cms.models;

import java.time.LocalDate;

import com.sgdc.cms.annotations.CustomEmployeeID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "employees")
public class Employee extends User{
	@CustomEmployeeID
	private String employeeId;
	
	@ManyToOne
    @JoinColumn(name = "dept_id", nullable=false)    
    private Department department;

	@Column(name = "date_of_joining")
	private LocalDate dateOfJoining;

	@Column(name = "date_of_leaving")
	private LocalDate dateOfLeaving;

	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)	
	private LocalDate dateOfBirth;

	@Column(name = "is_employed")
	private boolean employed;

	@Column(name = "nationality")
    private String nationality;

	@Column(name = "gender")
    private String gender;

	@Column(name = "address")
    private String address;

    public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

    public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public LocalDate getDateOfLeaving() {
		return dateOfLeaving;
	}

	public void setDateOfLeaving(LocalDate dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}

	public boolean isEmployed() {
		return employed;
	}

	public void setEmployed(boolean employed) {
		this.employed = employed;
	}

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
