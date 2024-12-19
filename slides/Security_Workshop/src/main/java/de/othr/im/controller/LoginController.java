package de.othr.im.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import de.othr.im.model.User;

@Controller

public class LoginController {

	@RequestMapping ("/login")
	public String showLoginForm(Model model) {
			
		
		return "login";
	}
	
	
	@RequestMapping ( method=RequestMethod.GET, value="/prelogout")
	public String showPreLogout(HttpServletRequest request, HttpServletResponse response) {
	
		return "prelogout";
	}
		
	
	
}
