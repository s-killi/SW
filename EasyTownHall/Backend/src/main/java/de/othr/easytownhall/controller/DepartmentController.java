package de.othr.easytownhall.controller;

import de.othr.easytownhall.models.entities.department.Department;
import de.othr.easytownhall.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/{name}")
    public ResponseEntity<Department> getDepartment(@PathVariable String name) {
        Department opt_dep = departmentService.getDepartmentByName(name);
        if(opt_dep == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(opt_dep);
    }

    @GetMapping()
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAll();
        if(departments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(departments);
    }
}
