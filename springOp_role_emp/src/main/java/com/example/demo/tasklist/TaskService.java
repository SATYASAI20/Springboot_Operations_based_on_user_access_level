package com.example.demo.tasklist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

	public List tasklist_select(Task_Pojo taskpojo) {
		
		String valiations = login_valiation(taskpojo);
		List arraylist = new ArrayList<Object>();
		String role_id="";
		//successfully logged in 
		if(valiations.equals("true")) {
			
//			arraylist.add("success");
			String sql_select_tasklist  = "select * from tasklist where 1=1 ";
			
			if(taskpojo.getTaskid() != null) {
//				System.out.println("success1001");
				try {
					String sql_selectdata = sql_select_tasklist+" and taskid = ?";
					List<Map<String,Object>> sql_select_table = jtemp.queryForList(sql_selectdata, taskpojo.getTaskid());
					// if task id is incorrect
					if(sql_select_table.isEmpty()==false) {
						arraylist.add(sql_select_table);
					}else {
						arraylist.add("invalid task id, please check");
					}
					
				}catch(Exception e) {
					arraylist.add("data does not exits, please check");
				}
			}else if(taskpojo.getTaskstatus() != null) {
//				System.out.println("success11");

				//for roles 
//				try {
					String sql_roles = "select * from roles where role=? ";
//					System.out.println(taskpojo.getTaskstatus());
					List<Map<String, Object>> sql_roles_table = jtemp.queryForList(sql_roles,taskpojo.getTaskstatus());
					if(sql_roles_table.isEmpty() == false) {
						String role = "";
						
						for(Map sql_roles_obj : sql_roles_table) {
							//getting roleid from roles table 
							role_id = (String) sql_roles_obj.get("role_id");
							System.out.println("success22"+role_id);
						}
					}else {
						// validation for invalid role 
						arraylist.add("invalid role, please check your role status should be one of as follows Completed or Assigned or Created");
					}
//				}catch(Exception e) {
//					arraylist.add("invalid role, please check your role");
//				}
				// for tasklist_table
				String tasklist_table = sql_select_tasklist+"and task_status=?";
				System.out.println("success");
				//role id is passed as arguments 
				List<Map<String,Object>> tasklist_table_data = jtemp.queryForList(tasklist_table,role_id);
				System.out.println(tasklist_table_data);
				// if data not present in table 
				if(tasklist_table_data.isEmpty() == false) {
					arraylist.add(tasklist_table_data);	
				}
			}else {
				System.out.println(arraylist+"  empty122");
				// no id or status is given --------- or empty is given
				List<Map<String, Object>> no_id_or_status = jtemp.queryForList(sql_select_tasklist);
				
				arraylist.add(no_id_or_status);
				System.out.println(arraylist+"  empty");
				
			}
		}else {
			//failed to logged
			arraylist.add(valiations);
			
		}
		
		return arraylist;
	}
	
	public String login_valiation(Task_Pojo taskpojo){
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
		
		if((login_user==true && login_pass==true && login_condition_project_manager==true) || ((login_user==true && login_pass==true) || login_condition_project_manager==true) ) {
			result = "true";
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

