package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

    @GetMapping("/hallo")
    @ResponseBody
    public String HalloWelt() {
        return "Hallo Welt";
    }

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }

}
