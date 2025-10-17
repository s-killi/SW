package de.simon.uebung1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    @GetMapping("/home")
    @ResponseBody
    public String HelloWorld() {
        return "Hallo World!";
    }
    
}