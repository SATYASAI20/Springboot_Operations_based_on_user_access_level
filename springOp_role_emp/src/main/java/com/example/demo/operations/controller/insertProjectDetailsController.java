package com.example.demo.operations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.operations.pojo.Project_Details_Pojo;
import com.example.demo.operations.service.ProjectDetailsService;

@RestController
public class insertProjectDetailsController {
	
	@Autowired
	ProjectDetailsService project_details_obj;
	
	@PostMapping("project_details_insert")
	String insertProjectDetails(@RequestBody Project_Details_Pojo pojo_obj) {
		return project_details_obj.project_details(pojo_obj);
	}
	
}
