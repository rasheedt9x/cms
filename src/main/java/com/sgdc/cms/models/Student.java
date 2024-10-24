package com.sgdc.cms.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgdc.cms.annotations.CustomStudentID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "students")
public class Student extends User {   

    // @GeneratedValue(generator = "stu_id_gen")
    // @GenericGenerator(name = "stu_id_gen",type = StudentIDGenerator.class)
    // @GeneratorType(type = StudentIDGenerator.class,when = GenerationTime.INSERT)

	// @Column(name = "student_name", nullable = false)
	// private String name;

	    
    @Column(name = "student_id")
    @CustomStudentID
    private String studentId;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)    
	private StudentGroup group;

    @Column(name = "year_of_study")
    private int yearOfStudy;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
    private Date dateOfBirth;

	@Column(name = "address")
	private String address;

	@Column(name = "gender")
	private String gender;

	@Column(name = "nationality")
	private String nationality;

	@Column(name = "primary_phone")
    private String primaryPhone;

    @Column(name = "secondary_phone")
    private String secondaryPhone;

	@Column(name = "guardian_name")
    private String guardianName;

	@Column(name = "guardian_phone")        
    private String guardianPhone;

	@Column(name = "ssc_school")
    private String sscSchool;

	@Column(name = "ssc_year_of_passing")
    private String sscYearOfPassing;

	@Column(name = "ssc_marks")
    private String sscMarks;

	@Column(name = "intermediate_college")
    private String intermediateCollege;

	@Column(name = "intermediate_year_of_passing")
    private String intermediateYearOfPassing;

    @Column(name = "intermediate_course")
    private String intermediateMarks;

    @Column(name = "degree_course")
    private String degreeCourse;

    @Column(name = "second_language")
    private String secondLanguage;

    @Column(name = "caste")
    private String caste;

    @Column(name = "religion")
    private String religion;

    @Column(name = "student_aadhaar")
    private String studentAadhaar;
  
    @Column(name = "mother_aadhaar")
    private String motherAadhaar;

	@ManyToOne
    @JoinColumn(name = "dept_id")    
    private Department department;

	@Column(name = "image")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String image;

    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void setDateOfBirth(Date dateOfBirth) {
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

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}    



	public String getPrimaryPhone() {
		return this.primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getSecondaryPhone() {
		return this.secondaryPhone;
	}

	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	public String getGuardianName() {
		return this.guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianPhone() {
		return this.guardianPhone;
	}

	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}

	public String getSscSchool() {
		return this.sscSchool;
	}

	public void setSscSchool(String sscSchool) {
		this.sscSchool = sscSchool;
	}

	public String getSscYearOfPassing() {
		return this.sscYearOfPassing;
	}

	public void setSscYearOfPassing(String sscYearOfPassing) {
		this.sscYearOfPassing = sscYearOfPassing;
	}

	public String getSscMarks() {
		return this.sscMarks;
	}

	public void setSscMarks(String sscMarks) {
		this.sscMarks = sscMarks;
	}

	public String getIntermediateCollege() {
		return this.intermediateCollege;
	}

	public void setIntermediateCollege(String intermediateCollege) {
		this.intermediateCollege = intermediateCollege;
	}

	public String getIntermediateYearOfPassing() {
		return this.intermediateYearOfPassing;
	}

	public void setIntermediateYearOfPassing(String intermediateYearOfPassing) {
		this.intermediateYearOfPassing = intermediateYearOfPassing;
	}

	public String getIntermediateMarks() {
		return this.intermediateMarks;
	}

	public void setIntermediateMarks(String intermediateMarks) {
		this.intermediateMarks = intermediateMarks;
	}

	public String getDegreeCourse() {
		return this.degreeCourse;
	}

	public void setDegreeCourse(String degreeCourse) {
		this.degreeCourse = degreeCourse;
	}

	public String getSecondLanguage() {
		return this.secondLanguage;
	}

	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public String getCaste() {
		return this.caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getReligion() {
		return this.religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getStudentAadhaar() {
		return this.studentAadhaar;
	}

	public void setStudentAadhaar(String studentAadhaar) {
		this.studentAadhaar = studentAadhaar;
	}

	public String getMotherAadhaar() {
		return this.motherAadhaar;
	}

	public void setMotherAadhaar(String motherAadhaar) {
		this.motherAadhaar = motherAadhaar;
	}

	public void setStudentId(String stdId){
        this.studentId = stdId;
    }

    public String getStudentId(){
        return this.studentId;
    }
    
    public String getGroup(){
        return this.group.getGroupname();
    }
    
    public void setGroup(StudentGroup grp){
        this.group = grp;
    }

    public void setYearOfStudy(int year) {
        this.yearOfStudy = year;
    }

    public int getYearOfStudy(){
        return this.yearOfStudy;
    }

    public String toString(){
        return this.studentId;
    }
}
