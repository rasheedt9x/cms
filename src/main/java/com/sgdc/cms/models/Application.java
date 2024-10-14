package com.sgdc.cms.models;



import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;

    @Temporal(TemporalType.DATE)    
    private Date dateOfBirth;

    @Column(name = "username")
    private String username;

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

    @Column(name = "intermediate_marks")
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


    // @PrePersist
    // protected void onCreate() {
    //     this.id = UUID.randomUUID();
    // }

    public Long getId() {
        return this.id;
    }
        
    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }	

     
    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }	


     
    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String ntly){
        this.nationality = ntly;
    }	

     
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String addr){
        this.address = addr;
    }	



    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }	

    public Date getDateOfBirth(){
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date date){
        this.dateOfBirth = date;
    }

    public ApplicationStatus getStatus() {
        return this.status;
    }

    public void setStatus(ApplicationStatus status){
        this.status = status;
    }




    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    
}
