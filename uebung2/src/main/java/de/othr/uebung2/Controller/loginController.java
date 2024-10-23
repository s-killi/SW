package de.othr.uebung2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
public class loginController {

    @GetMapping("/login")
    public String showLoginMethode() {
        return "/login/loginscreen";
    }

    @PostMapping("/login/process")
    public String processLoginMethode(
        @RequestParam String name, 
        @RequestParam String password,
        @RequestParam String role,
        Model model) {
        
        model.addAttribute("name", name);
        model.addAttribute("password", password);
        model.addAttribute("role", role);


        return "/login/logedinscreen";
    }
        
}
