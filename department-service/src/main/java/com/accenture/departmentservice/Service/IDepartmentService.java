package com.accenture.departmentservice.Service;

import com.accenture.departmentservice.Entity.Department;

import java.util.List;

public interface IDepartmentService {
	
	Department saveDepartment(Department department);

    Department getDepartmentById(Integer departmentId);

   List<Department> getALLDepartments();

   Department updateDepartment(Department department);

}
