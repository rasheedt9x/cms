package com.sgdc.cms.dto;

public class StudentDto {
    
    private String username;
    private String password;
    private String name;
    private String group;
    private int yearOfStudy;
    private String email;


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

    public void setPassword(String password) {
        this.password = password;
    }
       
    public String getPassword() {
        return this.password;
    }


    
    public String getGroup(){
        return this.group;
    }

    public void setGroup(String grp){
        this.group = grp;
    }

    public void setYearOfStudy(int year) {
        this.yearOfStudy = year;
    }

    public int getYearOfStudy(){
        return this.yearOfStudy;
    }
}
