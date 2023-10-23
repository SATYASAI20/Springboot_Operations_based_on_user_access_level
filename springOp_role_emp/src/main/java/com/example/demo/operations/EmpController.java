package com.example.demo.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpController {
	
	@Autowired
	EmpServices service_obj;
	
	@PostMapping("insert")
	String insertOp(@RequestBody EmpPojo pojo_obj){
		
		return service_obj.insert(pojo_obj);
		
		
		
	}
}
//return "please check your login details";
