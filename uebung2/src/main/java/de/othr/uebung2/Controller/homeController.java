package de.othr.uebung2.Controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class homeController {
    
    @GetMapping("/home")
    @ResponseBody
    public String home() {
        return "home";
    }

    @GetMapping("/pathvariable")
    public String showPathVaraiablePAge(Model model) {

        ArrayList<String> postids = new ArrayList<>();
        postids.add("1");
        postids.add("2");
        postids.add("3");
        postids.add("4");

        model.addAttribute("postids", postids);

        return "pathvariable";
    }

    @GetMapping("/pathvariable/process/{postid}")
    public String showPathVaraiable(@PathVariable String postid, Model model) {
        model.addAttribute("postid", postid);
        return "pathvariable-process";
    }
    
}
