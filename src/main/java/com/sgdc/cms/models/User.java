package com.sgdc.cms.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;

@Entity
public class User extends Person{

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
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

    public boolean isEnabled(){
        return this.enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;   
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void addRoles(Role role){
        this.roles.add(role);
    }
}
