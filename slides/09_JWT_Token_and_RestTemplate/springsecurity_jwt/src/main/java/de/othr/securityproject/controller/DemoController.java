package de.othr.securityproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")

@RestController
public class DemoController {

	

	@GetMapping("/demo")
	public ResponseEntity<String> helloDemo(){
		
				
		return ResponseEntity.ok("User is authenticated!");
	}
	
}
