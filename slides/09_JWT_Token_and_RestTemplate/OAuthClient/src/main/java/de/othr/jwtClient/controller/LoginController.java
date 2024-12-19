package de.othr.jwtClient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

	
	@GetMapping("/signin")
    public String showSigin(){
	 
		System.out.println("Passing in the LoginController...");
	 	
        return "signin2";
 }
 
}
