package com.example.demo.project_emp_meetings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Project_Emp_Controller {
	
	@Autowired
	Project_Emp_Service Project_Emp_Service_obj;
	
	@PostMapping("emp_meeting")
	public String project_emp_meetings(@RequestBody Meetings_Pojo Project_Emp_Pojo_obj) {
		System.out.println(Project_Emp_Pojo_obj.getLogin_user_name());
		System.out.println("enter controller");
		return Project_Emp_Service_obj.project_emp_meetings(Project_Emp_Pojo_obj);
	}
}
