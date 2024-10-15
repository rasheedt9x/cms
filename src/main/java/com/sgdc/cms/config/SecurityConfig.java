package com.sgdc.cms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;


import com.sgdc.cms.repositories.EmployeeRepository;
import com.sgdc.cms.repositories.StudentRepository;
import com.sgdc.cms.security.jwt.JwtAuthenticationFilter;
import com.sgdc.cms.security.jwt.JwtTokenProvider;
import com.sgdc.cms.services.LoginDetailsService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private LoginDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // @Autowired
    // public SecurityConfig(LoginDetailsService userDetailsService) {
    //     this.userDetailsService = userDetailsService;
    // }
            
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
            (request) -> {
	            request            
	        .requestMatchers("/","/auth/**", "/api/v1/applications/new").permitAll()
            // Allow ADMIN and ADMISSION_MANAGER to access specific API endpoints
            
           .requestMatchers("/api/v1/students/get/**").hasRole("STUDENT")                
              .requestMatchers("/api/v1/groups/**", "/api/v1/students/**", "/api/v1/applications/**")
            .hasAnyRole("ADMIN", "ADMISSION_MANAGER")
           .anyRequest().authenticated(); // This should be the last statement
    });



        // http.formLogin(
        //     login -> {
        //         login.loginPage("/login").permitAll().defaultSuccessUrl("/home",true);      
        //     }
        // );
        // http.logout(
        //     logout -> {
        //         logout.permitAll();
        //     }  
        // );

        // http.formLogin(login -> {
        //     login.loginProcessingUrl("/login").successHandler((req,res,auth) -> {
        //         res.setContentType("application/json;charset=UTF=8");
        //         res.getWriter().write("{\"status\": \"success\", \"message\": \"Login successful\"}");
        //     }).failureHandler((req,res,auth) -> {
        //        res.setContentType("application/json;charset=UTF-8");
        //        res.setStatus(401);
        //        res.getWriter().write("{\"status\": \"error\",\"message\": \"Invalid Credentials\"}"); 
        //     });
        // });

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider,userDetailsService ),UsernamePasswordAuthenticationFilter.class );
        http.formLogin(AbstractHttpConfigurer::disable);                
        http.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID");
            logout.clearAuthentication(true);    
            logout.logoutSuccessHandler((req,res,auth) -> {                     
                res.getWriter().write("{\"status\": \"success\", \"message\": \"Logout successful\"}");
            });
        }); 
 //       http.csrf(AbstractHttpConfigurer::disable);
        http.csrf(cust -> {
           cust.disable(); 
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
