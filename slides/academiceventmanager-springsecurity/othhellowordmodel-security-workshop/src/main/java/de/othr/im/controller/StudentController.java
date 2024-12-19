package de.othr.im.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.othr.im.model.AcademicEvent;
import de.othr.im.model.Lecture;
import de.othr.im.model.Student;
import de.othr.im.model.Workshop;
import de.othr.im.model.WorkshopSubscription;
import de.othr.im.repository.WorkshopRepository;
import de.othr.im.repository.WorkshopSubscriptionRepository;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	WorkshopSubscriptionRepository workshopSubscriptionRepository;
	
	@Autowired
	WorkshopRepository workshopRepository;
	
	@RequestMapping(value ="/workshopsubscription/all")
	public String showPaginatedWorkshopSubscriptions(
			@RequestParam(defaultValue = "0", required = false, name = "page") int informedpage, 
			@RequestParam(defaultValue = "10", required = false, name = "size") int informedpagesize, 
			HttpServletRequest request) {
		
      		
		
			Student student = (Student) request.getSession().getAttribute("studentSession");
			System.out.println("logged student is "+student.getUser().getLogin());
						
			Pageable pageable = PageRequest.of(informedpage, informedpagesize, Sort.by("workshop.date").descending());
			
			
			System.out.println("Searching for the idstudent="+ student.getId());
			List<WorkshopSubscription> workshopSubscriptions = workshopSubscriptionRepository.findAllWithPagination(student.getId(), pageable);
			
			System.out.println("Workshops Subscriptions found ="+ workshopSubscriptions.size());
			
			request.getSession().setAttribute("workshopSubscriptions", workshopSubscriptions);
			
			request.setAttribute("page", informedpage+1);
			request.setAttribute("previouspage", informedpage>0? informedpage-1 : 0);
			request.setAttribute("size", informedpagesize);
			
	 
		return "/student/student-workshops";
	}
	
	@RequestMapping("/workshopsubscription/add")
	public String showAddWorkShopSubscriptionForm(Model model, HttpServletRequest request) {
		
		WorkshopSubscription workshopSubscription = new WorkshopSubscription();
		
		workshopSubscription.setWorkshop(new Workshop());
		
		
		model.addAttribute("workshopsubscriptionForm", workshopSubscription);
		
		request.getSession().setAttribute("workshopSubscriptionSession", workshopSubscription);
			
		
		return "/student/student-workshopsubscription-add";
	}
	
	

	@RequestMapping(value= "/workshopsubscription/add/process")
	public ModelAndView addWorkshopSubscriptionProcess(@Valid @ModelAttribute("workshopSubscriptionForm") WorkshopSubscription workshopSubscriptionForm, BindingResult bindingResult,  Model model, HttpServletRequest request) {
	
		ModelAndView mv = new ModelAndView();
			
			
					
		if (bindingResult.hasErrors()) {
			
			mv.setViewName("/student/student-workshopsubscription-add");
			
			return mv;
		}
		
		System.out.println("Title of the Workshop in workshopSubscription="+ workshopSubscriptionForm.getWorkshop().getTitle());
		Student student  = (Student) request.getSession().getAttribute("studentSession");
		
		workshopSubscriptionForm.setStudent(student);
		
		Optional<Workshop> workshopOp = workshopRepository.findById(workshopSubscriptionForm.getWorkshop().getId());
		
		workshopSubscriptionForm.setWorkshop(workshopOp.get());
		
		WorkshopSubscription workshopSubscription = workshopSubscriptionRepository.save(workshopSubscriptionForm);
			
		System.out.println("Id Saved="+ workshopSubscription.getId());
		
		mv.setViewName( "forward:/student/workshopsubscription/all/");
				
		return mv;
	
		
	}
	
	
	//show a Workshop search form
	@RequestMapping(value= "/workshopsubscription/selectworkshop")
		
	public String showFormSelectWorkshopByID(Model model) {
				
		Workshop workshop = new Workshop();
		workshop.setId((long) -1);
		model.addAttribute("workshopForm", workshop);
		//model.addAttribute("workshops", workshops);
				
		return "/student/student-workshopsubscription-selectworkshop";
		
	}
	
	
	//search workshops by the title in the DB and returns To the same page 
	
	@RequestMapping(value= "/workshopsubscription/selectworkshop2")
	public String searchWorkshop(@ModelAttribute(name = "workshopForm") Workshop workshop, Model model) {
		
		
		System.out.println("Searching for workshops with title=" +workshop.getTitle());
		List<Workshop> workshops = workshopRepository
				.findByTitleContaining(workshop.getTitle());
		System.out.println("Found "+ workshops.size());
		model.addAttribute("workshops", workshops);
		
		
		
	return "/student/student-workshopsubscription-selectworkshop";
	}
	
	
    //receive the ID-Workshop and goes back to the WokshopSubscription-add Form 
	@RequestMapping(value = "/workshopsubscription/selectworkshop/{id}")
	public String selectWorkshopProcess(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		 
		WorkshopSubscription workshopSubscriptionSession = (WorkshopSubscription) request.getSession().getAttribute("workshopSubscriptionSession");
		
		Optional<Workshop> workshop  = workshopRepository.findById((long) id);
		workshopSubscriptionSession.setWorkshop(workshop.get());
					
		request.getSession().setAttribute("workshopSubscriptionSession", workshopSubscriptionSession);
		model.addAttribute("workshopsubscriptionForm", workshopSubscriptionSession);
		
		return  "/student/student-workshopsubscription-add";
	}	
	
	
	@RequestMapping(value= "/workshopsubscription/delete/{id}" , method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model) {
				
		Optional<WorkshopSubscription> workshopSubscriptionOpt = workshopSubscriptionRepository.findById(id);
				
		//Gibt es!
		if (!workshopSubscriptionOpt.isEmpty()) {
			
			workshopSubscriptionRepository.delete(workshopSubscriptionOpt.get());
			model.addAttribute("msgs", "Subscription canceled!");
		}
		else {
			model.addAttribute("errors","Subscription not found!");
			
		}
				
		return "forward:/student/workshopsubscription/all";
		
	}
	
	
	
	

}
