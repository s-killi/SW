package de.othr.helloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	
	@GetMapping("/hallo")
	@ResponseBody
	public String HelloWorld() {
		
		
		
		return "Hallo Welt!";
	}

	@GetMapping("/home")
	
	public String HelloWorldHomeView() {
		
		
		
		return "home";
	}
}
