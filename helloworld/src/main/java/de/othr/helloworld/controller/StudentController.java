package de.othr.helloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/student/")

public class StudentController {
	
	
	@GetMapping("add")
	public String showAddStudentForm() {
		
		return "student/student-add";
	}
	
	
	@PostMapping("add/process")
	public String processAddStudentForm() {
		
		
		return "student/student-add-success";
	}

}
