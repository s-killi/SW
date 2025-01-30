package de.othr.easytownhall.controller;

import de.othr.easytownhall.models.entities.department.Department;
import de.othr.easytownhall.services.appointments.WorkscheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/appointment")
public class AppointmentController {

    @Autowired
    private WorkscheduleService workscheduleService;


}
