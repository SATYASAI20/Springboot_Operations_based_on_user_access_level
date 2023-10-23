package com.example.demo.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {
	
	@Autowired
	service sobj;
	
	@PostMapping("insert")
	String insertOp(@RequestBody pojo cobj){
		
		return sobj.insert(cobj);
	}
}
