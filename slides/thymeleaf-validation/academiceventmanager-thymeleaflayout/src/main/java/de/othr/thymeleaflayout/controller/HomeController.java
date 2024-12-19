package de.othr.thymeleaflayout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	//uses the whole html code
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String home() {
		
		
		return "home";
	}
	
	
	//uses the thymeleaf layout dialect	
	@RequestMapping(method = RequestMethod.GET, value = "/content")
	public String layout() {
		return "content";
	}
	
	//uses the thymeleaf layout dialect	
		@RequestMapping(method = RequestMethod.GET, value = "/admin")
		public String layoutadm() {
			return "content-adm";
		}
}
