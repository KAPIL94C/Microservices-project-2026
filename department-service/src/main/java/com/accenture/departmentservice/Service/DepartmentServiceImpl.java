package com.accenture.departmentservice.Service;

import com.accenture.departmentservice.DepartmentServiceApplication;
import com.accenture.departmentservice.Entity.ErrorResponse;
import com.accenture.departmentservice.Exception.DepartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.departmentservice.Dao.DepartmentRepository;
import com.accenture.departmentservice.Entity.Department;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department saveDepartment(Department department) {

        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {

            return departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException("Department with ID " + departmentId + " does not exist"));


    }

    @Override
    public List<Department> getALLDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department updateDepartment(Department department) {

        Department department1 = departmentRepository.findById(department.getId()).orElseThrow(() -> new DepartmentNotFoundException("Department with ID " + department.getId() + " does not exist"));
        department1.setDepartmentAddress(department.getDepartmentAddress());
        department1.setDepartmentCode(department.getDepartmentCode());
        department1.setDepartmentName(department.getDepartmentName());
        return departmentRepository.save(department1);

    }
}

