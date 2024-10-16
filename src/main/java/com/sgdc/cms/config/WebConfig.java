package com.sgdc.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry){
	    registry.addMapping("/**").allowedOrigins("http://localhost:5173")  // Allow your frontend origin
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Specify the HTTP methods allowed
				.allowedHeaders("*")  // Allow all headers
				.allowCredentials(true);
	}
}
