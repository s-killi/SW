package de.othr.im.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import de.othr.im.model.Manager;
import de.othr.im.model.Professor;
import de.othr.im.model.Student;
import de.othr.im.model.User;
import de.othr.im.repository.ManagerRepository;
import de.othr.im.repository.ProfessorRepository;
import de.othr.im.repository.StudentRepository;
import de.othr.im.repository.UserRepository;
import de.othr.im.util.Properties;

@Controller
public class HomeController {
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ManagerRepository managerRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	ProfessorRepository professorRepository;
	
	@RequestMapping(value={"/", "/home"})
	public String home(HttpServletRequest request, Principal principal) {
			
				
		List <GrantedAuthority> authorities= (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		String myAuthorities = authorities.toString();	
			
		System.out.println(
				"Authorities of the logged user "+principal.getName()+ " is/are"  + " = " +
				myAuthorities
		);
			
		//searching in the database by the login....
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		
		if (myAuthorities.contains("ADMIN")){
					
			Optional<Manager> oManager; 
			
			
				oManager= managerRepository.findManagerByIdUser(oLoggedUser.get().getId());
				request.getSession().setAttribute("managerSession", oManager.get());
			
			
			return "manager";
		
		}	
		if  (myAuthorities.contains("STUDENT")){
			
			Optional<Student> oStudent; 
			
		
				oStudent= studentRepository.findStudentByIdUser(oLoggedUser.get().getId());
				request.getSession().setAttribute("studentSession", oStudent.get());
			
			
			return "student";
			
		}
		if (myAuthorities.contains("PROFESSOR")){//  (perfil.equalsIgnoreCase("PROFESSOR"))
			
			Optional<Professor> oProfessor; 
			
				oProfessor= professorRepository.findProfessorByIdUser(oLoggedUser.get().getId());
				request.getSession().setAttribute("professorSession", oProfessor.get());
				
			return "professor";
		}
		
		return "/error";
		
	}

}
