package com.accenture.departmentservice.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.departmentservice.Entity.Department;


@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer>{

}
