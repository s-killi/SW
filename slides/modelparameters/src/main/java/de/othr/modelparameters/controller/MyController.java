package de.othr.modelparameters.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class MyController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(required = false) String message) {


    	System.out.println("message received:"+ message);
    	
        return "greeting";
    }
    
    @GetMapping("/greetingmodel")
    public ModelAndView greeting(@RequestParam(required = false) String message, Model model) {
       
    	
    	System.out.println("name="+message);
    	
    	ModelAndView mv = new ModelAndView("greeting");
    	mv.addObject("message", "Hello, " + message+ "!");
    
    	
        return mv;
    }
    
    @GetMapping("/mapparams")
    public String getMethodName() {
        return "mapparams-form";
    }
    
    @PostMapping("/mapparams/process")
    public String processMapparams(@RequestParam Map <String, String> params, Model model) {
        //TODO: process POST request
        
    	System.out.println(params);
    	model.addAttribute("params", params);
        return "mapparams-processed";
    }
    
    
    
   
}