package de.othr.securityproject.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

/*
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String home() {
	
	
		return "home";
	}
	
	*/
	
	@RequestMapping(value={"/", "/home"})
	public String home2(HttpServletRequest request) {
			
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
		   username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		System.out.println("user logged = "+ username);
		request.getSession().setAttribute("login", username);
		
		return "home";
		
	}

		

	
}
