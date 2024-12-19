package de.othr.modelparameters.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.othr.modelparameters.model.Student;




@Controller
public class StudentController {
	
	
	@RequestMapping(value = "/student/add", method = RequestMethod.GET)
	public String showStudentAddModelForm( Model model) {
				
		Student student = new Student();
		student.setAge(20);
		
		model.addAttribute("student", student);
				
		return "student/student-add";
	}
	
	
	@RequestMapping(value = "/student/add/process")
	public ModelAndView processStudentAddModelForm
	(@ModelAttribute Student student, Model model) {
		
		System.out.println(student.getName());
	
		//student object validate
		//student object save
		
		ModelAndView mv = new ModelAndView("student/student-added.html");
		mv.addObject("student", student);
		
		return mv;
	}
	
	
	
	
	

}

