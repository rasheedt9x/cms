package com.sgdc.cms.dto;

public class StudentDto {
    
    private String name;
    private String email;
    private String nationality;
    private String gender;
    private String address;
    private String dateOfBirth;
    private String username;
    private String primaryPhone;
    private String secondaryPhone;
    private String guardianName;
    private String guardianPhone;
    private String sscSchool;
    private String sscYearOfPassing;
    private String sscMarks;
    private String intermediateCollege;
    private String intermediateYearOfPassing;
    private String intermediateMarks;
    private String degreeCourse;
    private String secondLanguage;
    private String caste;
    private String religion;
    private String studentAadhaar;
    private String motherAadhaar;
    private int yearOfStudy;

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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getSecondaryPhone() {
		return secondaryPhone;
	}

	public void setSecondaryPhone(String secondaryPhone) {
		this.secondaryPhone = secondaryPhone;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianPhone() {
		return guardianPhone;
	}

	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}

	public String getSscSchool() {
		return sscSchool;
	}

	public void setSscSchool(String sscSchool) {
		this.sscSchool = sscSchool;
	}

	public String getSscYearOfPassing() {
		return sscYearOfPassing;
	}

	public void setSscYearOfPassing(String sscYearOfPassing) {
		this.sscYearOfPassing = sscYearOfPassing;
	}

	public String getSscMarks() {
		return sscMarks;
	}

	public void setSscMarks(String sscMarks) {
		this.sscMarks = sscMarks;
	}

	public String getIntermediateCollege() {
		return intermediateCollege;
	}

	public void setIntermediateCollege(String intermediateCollege) {
		this.intermediateCollege = intermediateCollege;
	}

	public String getIntermediateYearOfPassing() {
		return intermediateYearOfPassing;
	}

	public void setIntermediateYearOfPassing(String intermediateYearOfPassing) {
		this.intermediateYearOfPassing = intermediateYearOfPassing;
	}

	public String getIntermediateMarks() {
		return intermediateMarks;
	}

	public void setIntermediateMarks(String intermediateMarks) {
		this.intermediateMarks = intermediateMarks;
	}

	public String getDegreeCourse() {
		return degreeCourse;
	}

	public void setDegreeCourse(String degreeCourse) {
		this.degreeCourse = degreeCourse;
	}

	public String getSecondLanguage() {
		return secondLanguage;
	}

	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getStudentAadhaar() {
		return studentAadhaar;
	}

	public void setStudentAadhaar(String studentAadhaar) {
		this.studentAadhaar = studentAadhaar;
	}

	public String getMotherAadhaar() {
		return motherAadhaar;
	}

	public void setMotherAadhaar(String motherAadhaar) {
		this.motherAadhaar = motherAadhaar;
	}

	public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }


    public void setUsername(String username) {
        this.username = username;
    }
       
    public String getUsername() {
        return this.username;
    }

    // public void setPassword(String password) {
    //     this.password = password;
    // }
       
    // public String getPassword() {
    //     return this.password;
    // }


    
    public String getGroup(){
        return this.degreeCourse;
    }

    public void setGroup(String grp){
        this.degreeCourse = grp;
     }

    public void setYearOfStudy(int year) {
        this.yearOfStudy = year;
    }

    public int getYearOfStudy(){
        return this.yearOfStudy;
    }
}
