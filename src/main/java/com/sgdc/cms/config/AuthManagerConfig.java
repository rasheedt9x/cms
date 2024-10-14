package com.sgdc.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sgdc.cms.repositories.EmployeeRepository;
import com.sgdc.cms.repositories.StudentRepository;
import com.sgdc.cms.services.LoginDetailsService;

@Configuration
public class AuthManagerConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(EmployeeRepository employeeRepository, StudentRepository studentRepository) {
        return new LoginDetailsService(employeeRepository, studentRepository);
    }
}
