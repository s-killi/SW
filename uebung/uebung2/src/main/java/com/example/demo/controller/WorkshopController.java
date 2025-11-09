package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WorkshopController {

    @GetMapping("/workshops/blabla/list")
    public String listWorkshops() {
        return "workshop/list";
    }

}
