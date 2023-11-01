package com.example.demo.tasklist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
	
	@Autowired
	JdbcTemplate jtemp;
	
	@Autowired
	NamedParameterJdbcTemplate jnamedtemp;
	
	public String tasklist(Task_Pojo taskpojo) {
		
		boolean login_user = false, login_pass=false, login_condition_project_manager=false;
		String current_login_user = ""; 								
		String current_login_pass = "";										
		String role_p = "P";
		String project_manager_username = "";
		String task_id = "";
		String result="";
		
		
		try {
			//project Details table and getting project manager name
			String project_table = "select * from projectDetails where projectid=?";
			List<Map<String, Object>> project_table_query= jtemp.queryForList(project_table, taskpojo.getProjectid());
			
			for(Map project_table_query_obj : project_table_query) {
				project_manager_username = (String) project_table_query_obj.get("PM");
				
			}
			if(project_manager_username == "") {
				result = "invalid project manager USER NAME, please check project manager user name";
				
			}
			
		}catch(Exception e) {
			result = "invalid project id, please check project id that you enterd!!";
		}
		
		
		
		// employee table
		String emp_sql = "Select * from employee where role = '"+role_p+"'";
		
		List<Map<String, Object>> emp_table= jtemp.queryForList(emp_sql);
		
		for(Map emp_table_obj : emp_table) {
			
			if(emp_table_obj.get("user_name").equals(taskpojo.getLogin_user_name())) {
				login_user = true;
				current_login_user = taskpojo.getLogin_user_name();
			}
			if (emp_table_obj.get("user_pass").equals(taskpojo.getLogin_pass())) {
				login_pass = true;
				current_login_pass = taskpojo.getLogin_pass();
			}
			if (emp_table_obj.get("user_name").equals(project_manager_username)) {
				login_condition_project_manager = true;
				//project_manager = taskpojo.getProject_manager_user_name();
				
			}
		}
		
		if(login_user==true && login_pass==true && login_condition_project_manager==true) {
			String current_task_name = taskpojo.getTask_name();
			
			// date taken 
			DateTimeFormatter date_time_format = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
			LocalDateTime now = LocalDateTime.now();  
			String current_date = date_time_format.format(now);
			
			System.out.println("start");
			char[] current_date_array = current_date.toCharArray();
			
			String current_task_date="";
			for(int i=0;i<current_date_array.length;i++) {
				System.out.println(i+" "+current_date_array[i]);
				if(i==3 || i==4 || i==8 || i==9) {
					current_task_date += current_date_array[i];
				}
			}
			
			//project id
			task_id += current_task_name+current_task_date;   		//25-10-2023 id=1023
			System.out.println(task_id);
			
			SqlParameterSource param = new MapSqlParameterSource()
					.addValue("projectid", taskpojo.getProjectid())
					.addValue("taskid", task_id)
					.addValue("taskname", current_task_name)
					.addValue("task_desc", taskpojo.getDescription())
					.addValue("task_duration", taskpojo.getDuration())
					.addValue("task_status", "C")
					.addValue("task_createdBy",project_manager_username);
			String insert_sql = "insert into tasklist values("
					+ ":projectid, :taskid, :taskname, :task_desc, :task_duration, :task_status, :task_createdBy)";
			
			try {
				
				int success = jnamedtemp.update(insert_sql, param);
				if(success != 0) {
					result = "successfully inserted to tasklist";
				}
			}catch(Exception e) {
				result = "failed to insert to tasklist, sql error ";
			}
		}
		
		//login validation username
		if(current_login_user=="") {
			result = "invalid user name, please check your user name";
		
		}
		//login password validate
		if(current_login_pass == "") {
			result = "invalid PASSWORD, please check your PASSWORD";
			
		}
		return result;
	}
	
	
	public String tasklist_Update(Task_Pojo taskpojo) {
		
		boolean login_user = false, login_pass=false, login_condition_project_manager=false;
		String current_login_user = ""; 								
		String current_login_pass = "";										
		String role_p = "P";
		String project_manager_username = "";
//		String task_id = "";
		String result="";
		
		
		try {
			//project Details table and getting project manager name
			String project_table = "select * from tasklist where taskid = ?";	
			List<Map<String, Object>> project_table_query= jtemp.queryForList(project_table, taskpojo.getTaskid());
			
			for(Map project_table_query_obj : project_table_query) {
				project_manager_username = (String) project_table_query_obj.get("task_createdBy");
				
			}
			if(project_manager_username == "") {
				result = "invalid project manager USER NAME, please check project manager user name";
				
			}
			
		}catch(Exception e) {
			result = "invalid task id, please check task id that you entered!!";
		}
		
		
		
		// employee table
		String emp_sql = "Select * from employee where role = '"+role_p+"'";
		
		List<Map<String, Object>> emp_table= jtemp.queryForList(emp_sql);
		
		for(Map emp_table_obj : emp_table) {
			
			if(emp_table_obj.get("user_name").equals(taskpojo.getLogin_user_name())) {
				login_user = true;
				current_login_user = taskpojo.getLogin_user_name();
			}
			if (emp_table_obj.get("user_pass").equals(taskpojo.getLogin_pass())) {
				login_pass = true;
				current_login_pass = taskpojo.getLogin_pass();
			}
			if (emp_table_obj.get("user_name").equals(project_manager_username)) {
				login_condition_project_manager = true;
				//project_manager = taskpojo.getProject_manager_user_name();
				
			}
		}
		
		if(login_user==true && login_pass==true && login_condition_project_manager==true) {
//			String current_role = "";
			System.out.println("entered0");
		
			try {
				String insert_sql = "update tasklist set task_desc = ? , task_duration = ? where taskid = ?";
				int success = jtemp.update(insert_sql, taskpojo.getDescription(),taskpojo.getDuration(),taskpojo.getTaskid());
				System.out.println("success2 "+success);
				
				if(success != 0) {
					System.out.println("success2"+success);
					result = "successfully updated to tasklist";
				}
			}catch(Exception e) {
				result = "failed to insert to tasklist, sql error ";
			}
		}
		
		//login validation username
		if(current_login_user=="") {
			result = "invalid user name, please check your user name";
				
		}
		//login password validate
		if(current_login_pass == "") {
			result = "invalid PASSWORD, please check your PASSWORD";	
		}
		
		
		return result;
		
	}
	
	
	public String tasklist_delete(Task_Pojo taskpojo) {
		boolean login_user = false, login_pass=false, login_condition_project_manager=false;
		String current_login_user = ""; 								
		String current_login_pass = "";										
		String role_p = "P";
		String project_manager_username = "";
//		String task_id = "";
		String result="";
		
		
		try {
			//project Details table and getting project manager name
			String project_table = "select * from tasklist where taskid = ?";	
			List<Map<String, Object>> project_table_query= jtemp.queryForList(project_table, taskpojo.getTaskid());
			
			for(Map project_table_query_obj : project_table_query) {
				project_manager_username = (String) project_table_query_obj.get("task_createdBy");
				
			}
			if(project_manager_username == "") {
				result = "invalid project manager USER NAME, please check project manager user name";
				
			}
			
		}catch(Exception e) {
			result = "invalid task id, please check task id that you entered!!";
		}
		
		
		
		// employee table
		String emp_sql = "Select * from employee where role = '"+role_p+"'";
		
		List<Map<String, Object>> emp_table= jtemp.queryForList(emp_sql);
		
		for(Map emp_table_obj : emp_table) {
			
			if(emp_table_obj.get("user_name").equals(taskpojo.getLogin_user_name())) {
				login_user = true;
				current_login_user = taskpojo.getLogin_user_name();
			}
			if (emp_table_obj.get("user_pass").equals(taskpojo.getLogin_pass())) {
				login_pass = true;
				current_login_pass = taskpojo.getLogin_pass();
			}
			if (emp_table_obj.get("user_name").equals(project_manager_username)) {
				login_condition_project_manager = true;
				//project_manager = taskpojo.getProject_manager_user_name();
				
			}
		}
		
		if(login_user==true && login_pass==true && login_condition_project_manager==true) {
			String sql_delete_select  = "select * from tasklist where taskid = ?";
			List<Map<String,Object>> selected_data_table = jtemp.queryForList(sql_delete_select,taskpojo.getTaskid());
			
			try {
				String insert_sql = "delete from tasklist where taskid = ?";
				int success = jtemp.update(insert_sql, taskpojo.getTaskid());
				System.out.println("success2 "+success);
				
				if(success != 0) {
					System.out.println("success2"+success);
					result = "successfully deleted to task "+taskpojo.getTaskid() ;
				}
			}catch(Exception e) {
				result = "failed to delete to task, sql error ";
			}
		}
		
		//login validation username
		if(current_login_user=="") {
			result = "invalid user name, please check your user name";
						
		}
		//login password validate
		if(current_login_pass == "") {
			result = "invalid PASSWORD, please check your PASSWORD";	
		}
		return result;
	}
}

