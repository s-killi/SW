package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {

    @GetMapping("/addstudent")
    public String addStudent() {
        return "student/add";
    }

    @GetMapping("/login")
    public String login() {
        return "form";
    }

    @PostMapping("/logingin")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {
        return username + "+" + password;
    }

}
