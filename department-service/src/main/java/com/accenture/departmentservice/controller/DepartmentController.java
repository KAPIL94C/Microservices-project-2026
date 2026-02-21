package com.accenture.departmentservice.controller;

import com.accenture.departmentservice.Entity.Department;
import com.accenture.departmentservice.Service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping
    public List<Department> getALLDepartments() {
        return departmentService.getALLDepartments();
    }

    @PostMapping
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
        department.setId(id);
        Department updatedDepartment = departmentService.updateDepartment(department);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable("id") Integer departmentId) {

        Department department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(department);

    }


}
