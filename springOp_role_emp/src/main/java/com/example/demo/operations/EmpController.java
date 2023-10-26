package com.example.demo.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpController {
	
	@Autowired
	EmpServices service_obj;
	
	@Autowired
	ProjectDetailsService project_details_obj;
	
	@Autowired
	ProjectEmpDetails_Service project_emp_details;
	
	@Autowired
	New_Project newproject;
	
	
	@PostMapping("insert")
	String insertOp(@RequestBody EmpPojo pojo_obj){
		
		return service_obj.insert(pojo_obj);
		
	}
	
	
	@PostMapping("project_details_insert")
	String insertProjectDetails(@RequestBody EmpPojo pojo_obj) {
		return project_details_obj.project_details(pojo_obj);
	}
	
	
	@PostMapping("project_emp_insert")
	String insertProjectEmpDetails(@RequestBody EmpPojo pojo_obj) {
		return project_emp_details.emp_details(pojo_obj);
	}
	
	
	
	@PostMapping("new_project")
	String copyProjectDetails(@RequestBody EmpPojo pojo_obj) {
		return newproject.newprojectOp(pojo_obj);
	}
	
	
	
	
	
	
	
	
	
}
