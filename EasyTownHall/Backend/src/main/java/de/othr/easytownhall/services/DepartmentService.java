package de.othr.easytownhall.services;

import de.othr.easytownhall.models.entities.department.Department;
import de.othr.easytownhall.models.entities.department.ServiceWorker;
import de.othr.easytownhall.repositories.DepartmentRepository;
import de.othr.easytownhall.repositories.ServiceWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ServiceWorkerRepository serviceWorkerRepository;

    public Department getDepartmentByName(String name) {
        Optional<Department> department = departmentRepository.findByName(name);
        if (department.isPresent()) {
            return department.get();
        }
        return null;
    }

    public Department getDepartmentById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            return department.get();
        }
        return null;
    }

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public List<ServiceWorker> getAllServiceWorkers(Long departmentId) {
        return serviceWorkerRepository.findByDepartmentId(departmentId);
    }
}
