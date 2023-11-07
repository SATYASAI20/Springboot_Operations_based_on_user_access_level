package com.example.demo.emptasklist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Emptasklist_Controller {
	
	@Autowired
	Emptasklist_Service emptasklistService_obj;
	
	//API call for completed task and employee taskList
	@PostMapping("competed_tasklist_emp_task")
	String tasklist_completed(@RequestBody Emptask_Pojo Emptask_pojo_obj) {
		System.out.println(121);
		System.out.println(Emptask_pojo_obj.getLogin_user_name());
			return emptasklistService_obj.emptasklist_completed(Emptask_pojo_obj);
	}
	
	@PostMapping("emptask_list_insert")
	String emptask_insert(@RequestBody Emptask_Pojo emptask_pojo) {
		return emptasklistService_obj.emptasklist_insert(emptask_pojo);	
	}
	
	
	@PostMapping("emptask_list_select")
	List emptasklist_select(@RequestBody Emptask_Pojo emptask_pojo) {
		
		
		return emptasklistService_obj.emptasklist_select(emptask_pojo);
		
	}
	
}
