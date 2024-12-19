package de.othr.modelparameters.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String showHome() {
		
		
		return "home";
	}
	
	
	@RequestMapping(value = "/pathvariable")
	public String showPathVariableForm (Model model) {
		
		ArrayList<String> postids = new ArrayList<String>();
		postids.add("1");
		postids.add("2");
		postids.add("3");
		postids.add("4");
		postids.add("5");
		model.addAttribute("postids", postids);
		return "pathvariable-form";
	}
	
	@RequestMapping(value = "/pathvariable/viewpost/{postid}" , method = RequestMethod.GET)
	public String PostPV (@PathVariable String postid , Model model) {
				
		System.out.println("Showing the POST "+ postid);	
		
		model.addAttribute("postid", postid);
		
		return "pathvariable-processed";
		
	}

	
	
	
	@RequestMapping(value = "/forward")
	public String showForwardForm (Model model) {
			
		return "forward";
	}
	
	@RequestMapping(value = "/forward/process")
	public String ProcessForwardForm (@RequestParam String name) {
		
		System.out.println("name in forward/process is .."+ name);
		return "forward:/forwardprocess2";
	}
	
	@RequestMapping(value = "/forwardprocess2")
	public String ProcessForwardForm2 (@RequestParam String name) {
		
		System.out.println("name in forwardprocess2 is .."+ name);		
		
		return "forward-processed";
	}
	
	
	@RequestMapping(value = "/redirect")
	public String showREdirectForm (Model model) {
		
		
		return "redirect";
	}
	
	
	@RequestMapping(value = "/redirect/process")
	public String ProcessRedirectForm (@RequestParam String name) {
		
		System.out.println("name in redirect/process is .."+ name);
		return "redirect:/redirectprocess2";
	}
	
	@RequestMapping(value = "/redirectprocess2")
	public String ProcessRedirectForm2 (@RequestParam(required = false) String name) {
		
		System.out.println("name in redirectprocess2 is .."+ name);		
		
		return "redirect-processed";
	}
	
}

