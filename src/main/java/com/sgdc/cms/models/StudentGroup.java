package com.sgdc.cms.models;

import ch.qos.logback.core.subst.Token.Type;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * StudentGroup
 */
@Entity
@Table(name = "groups")
public class StudentGroup {

	@Id
	@Column(name = "group_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name",unique = true,nullable = false)
    private String groupName;

    public StudentGroup(){}
    // public StudentGroup(String groupname) {
    //     this.groupName = groupname;
    // }
    
    public Long getId() {
        return this.id;
    }
    
    public void setGroupname(String groupName) {
        this.groupName = groupName;
    }   

    public String getGroupname() {
        return this.groupName;
    }
}
