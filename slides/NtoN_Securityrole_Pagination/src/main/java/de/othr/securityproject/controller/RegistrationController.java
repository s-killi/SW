package de.othr.securityproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.othr.securityproject.model.Address;
import de.othr.securityproject.model.Course;
import de.othr.securityproject.model.Registration;
import de.othr.securityproject.model.Student;
import de.othr.securityproject.model.Workshop;
import de.othr.securityproject.repository.WorkshopRepositoryI;
import de.othr.securityproject.service.RegistrationServiceI;
import de.othr.securityproject.service.StudentServiceI;
import de.othr.securityproject.service.WorkshopServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {
	
	private RegistrationServiceI registrationService;
	private StudentServiceI studentService;
	private WorkshopServiceI workshopService;
	
	public RegistrationController(RegistrationServiceI registrationService,
			StudentServiceI studentService,
			WorkshopServiceI workshopService) {
		
		this.registrationService=registrationService;
		this.studentService=studentService;	
		this.workshopService=workshopService;
		
	}
	
	@GetMapping("/add")
    public String showSignUpForm(Model model, HttpServletRequest request) {
		
		Registration registration = new Registration();
		
		Student student = new Student();		
		Workshop workshop = new Workshop();
		
		registration.setStudent(student);
		registration.setWorkshop(workshop);
		
		
		model.addAttribute("registration", registration);
		request.getSession().setAttribute("registrationSession", registration);
		
		
        return "/registrations/registration-add";
    }
	
	//processing the add..
    @PostMapping("/add")
    public String addRegistration(@Valid @ModelAttribute Registration registration, 
    		BindingResult result, 
    		Model model,
    		RedirectAttributes redirectAttributes) {
        
    	if (result.hasErrors()) {
    		System.out.println(result.getAllErrors().toString());
            return "/registrations/registration-add";
        }
     
    	registrationService.saveRegistration(registration);
        redirectAttributes.addFlashAttribute("added", "Registration in the workshop "+registration.getWorkshop().getDescription() +" added!");
        
        return "redirect:/registration/all";
    }
    
    
    /*Selecting a workshop*/
    
    //step 1:
    //showing the form to search a workshop...
	@GetMapping(value= "/workshop/select")
	public String showSelectWorkshopByID(Model model) {
		
		Workshop workshop = new Workshop();
		workshop.setId((long) -1);
		List <Workshop> workshops = new ArrayList<Workshop>();
		model.addAttribute("workshop", workshop);
		model.addAttribute("workshops", workshops);
				
		return "/registrations/registration-select-workshop";
		
	}
	
	//step 2:
	//processing the workshop search
	@PostMapping(value= "/workshop/select")
	public String showResultsSelectWorkshopByID(Model model, @ModelAttribute Workshop workshop) {
						
		System.out.println("searching for.."+ workshop.getDescription());
		List <Workshop> workshops = workshopService.findWorkshopsByDescription(workshop.getDescription());
		System.out.println("workshops found:"+ workshops.size());
		model.addAttribute("workshops", workshops);
		
						
		return "/registrations/registration-select-workshop";
		
	}
	
	//selecting a course and going back to the workshop-add page...
	
	//step 3:
	@RequestMapping(value = "/workshop/select/{id}")
	public String selectWorkshopProcess(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		
				
		Registration registrationSession = (Registration) request.getSession().getAttribute("registrationSession");
		
		Workshop workshop  = workshopService.getWorkshopById((long) id);
		
		System.out.println("select workshop id=" +workshop.getId());
				
		registrationSession.setWorkshop(workshop);
									
		request.getSession().setAttribute("registrationSession", registrationSession);
		
		model.addAttribute("registration", registrationSession);
		
		if (registrationSession.getId()>0) {
			return  "/registrations/registration-update";
		}
				
		return  "/registrations/registration-add";
	}	
	
    
    /*Selecting a student*/
    
    //step 1:
    //showing the form to search a student...
	@GetMapping(value= "/student/select")
	public String showSelectStudentByID(Model model) {
		
		Student student = new Student();
		student.setId((long) -1);
		List <Student> students = new ArrayList<Student>();
		model.addAttribute("student", student);
		model.addAttribute("students", students);
				
		return "/registrations/registration-select-student";
		
	}
	
	//step 2:
	//processing the student search
	@PostMapping(value= "/student/select")
	public String showResultsSelectStudentByID(Model model, @ModelAttribute Student student) {
						
		System.out.println("searching for.."+ student.getName());
		model.addAttribute("students", studentService.findStudentsByName(student.getName()));
						
		return "/registrations/registration-select-student";
		
	}
	
	//selecting a course and going back to the student-add page...
	
	//step 3:
	@RequestMapping(value = "/student/select/{id}")
	public String selectStudentProcess(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		
				
		Registration registrationSession = (Registration) request.getSession().getAttribute("registrationSession");
		
		Student student  = studentService.getStudentById((long) id);
		
		System.out.println("select student id=" +student.getId());
				
		registrationSession.setStudent(student);
									
		request.getSession().setAttribute("registrationSession", registrationSession);
		
		model.addAttribute("registration", registrationSession);
		
		if (registrationSession.getId()>0) {
			return  "/registrations/registration-update";
		}
				
		return  "/registrations/registration-add";
	}	
	
	@GetMapping("/all")
    public String showUserList(Model model) {
        
    	model.addAttribute("registrations", registrationService.getAllRegistrations());
    	
        return "/registrations/registration-all";
    }
    
    
}
