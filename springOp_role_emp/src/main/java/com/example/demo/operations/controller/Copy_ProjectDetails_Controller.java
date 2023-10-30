package com.example.demo.operations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.operations.pojo.New_ProjectOp_Pojo;
import com.example.demo.operations.service.New_ProjectService;

@RestController
public class Copy_ProjectDetails_Controller {
	
	@Autowired
	New_ProjectService newproject;

	@PostMapping("new_project")
	String copyProjectDetails(@RequestBody New_ProjectOp_Pojo pojo_obj) {
		return newproject.newprojectOp(pojo_obj);
	}
}
