package com.example.demo.tasklist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
	
	@Autowired
	TaskService taskserviceobj;
	
	@PostMapping("tasklist_insert")
	String tasklist_insert(@RequestBody Task_Pojo taskpojo) {
		
		return taskserviceobj.tasklist_insert(taskpojo);
	}
	
	
	@PostMapping("tasklist_update")
	String tasklist_update(@RequestBody Task_Pojo taskpojo) {
		return taskserviceobj.tasklist_Update(taskpojo);
		
	}
	
	@PostMapping("tasklist_delete")
	String tasklist_delete(@RequestBody Task_Pojo taskpojo) {
		return taskserviceobj.tasklist_delete(taskpojo);
		
	}
	
	@PostMapping("tasklist_select")
	List tasklist_select(@RequestBody Task_Pojo taskpojo) {
		return taskserviceobj.tasklist_select(taskpojo);
	}
	
}
