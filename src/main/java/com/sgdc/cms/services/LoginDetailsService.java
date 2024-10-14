package com.sgdc.cms.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sgdc.cms.models.User;
import com.sgdc.cms.repositories.EmployeeRepository;
import com.sgdc.cms.repositories.StudentRepository;

/**
 * LoginDetailsService
 */
@Service
public class LoginDetailsService implements UserDetailsService{
    private final Logger logger = LoggerFactory.getLogger(LoginDetailsService.class);

    private EmployeeRepository employeeRepository;
    private StudentRepository studentRepository;

    @Autowired
    public LoginDetailsService(EmployeeRepository er, StudentRepository sr) {
        this.employeeRepository = er;
        this.studentRepository = sr;
    }


	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = null;

        if (studentRepository.existsByUsername(username)) {
        	user = studentRepository.findByUsername(username).get();
        }

        if (user == null && employeeRepository.existsByUsername(username)) {
        	user = employeeRepository.findByUsername(username).get();
        } 
        if(user == null)
        {
            throw new UsernameNotFoundException("User not found");
        }

        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRolename())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
          user.getUsername(),
          user.getPassword(),
          authorities  
        );
    }
}
