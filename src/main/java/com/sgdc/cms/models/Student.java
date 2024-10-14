package com.sgdc.cms.models;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import com.sgdc.cms.annotations.CustomStudentID;
import com.sgdc.cms.generators.StudentIDGenerator;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Entity
@Table(name = "students")
public class Student extends User {   

        // @GeneratedValue(generator = "stu_id_gen")
    // @GenericGenerator(name = "stu_id_gen",type = StudentIDGenerator.class)
    // @GeneratorType(type = StudentIDGenerator.class,when = GenerationTime.INSERT)

    
    @Column(name = "student_id")
    @CustomStudentID
    private String studentId;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable=false)    
	private StudentGroup group;

    @Column(name = "year_of_study")
    private int yearOfStudy;

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
