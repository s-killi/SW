package de.othr.securityproject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
 

 
//@ImportResource("classpath:beans.xml")
@SpringBootApplication
public class AcademicProjectApplication1 {
	
	
	/*
	API CONSUMER IMPLEMENTATION WITH RESTTEMPLATE
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	*/
	
	public static void main(String[] args) {
		SpringApplication.run(AcademicProjectApplication1.class, args);
	}

}
