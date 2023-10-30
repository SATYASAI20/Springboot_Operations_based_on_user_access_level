package com.example.demo.operations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.operations.pojo.ProjectEmp_insert_pojo;
import com.example.demo.operations.service.ProjectEmpDetails_Service;

@RestController
public class Insert_ProjectEmpDetails_Controller {
	
	@Autowired
	ProjectEmpDetails_Service project_emp_details;
	
	@PostMapping("project_emp_insert")
	String insertProjectEmpDetails(@RequestBody ProjectEmp_insert_pojo pojo_obj) {
		return project_emp_details.emp_details(pojo_obj);
	}
}
