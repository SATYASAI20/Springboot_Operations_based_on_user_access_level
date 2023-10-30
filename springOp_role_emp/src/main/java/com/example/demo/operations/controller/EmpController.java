package com.example.demo.operations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.operations.pojo.EmpPojo;
import com.example.demo.operations.service.EmpServices;


@RestController
public class EmpController {
	
	@Autowired
	EmpServices service_obj;
	
	
	@PostMapping("insert")
	String insertOp(@RequestBody EmpPojo pojo_obj){
		
		return service_obj.insert(pojo_obj);
		
	}
	
}
