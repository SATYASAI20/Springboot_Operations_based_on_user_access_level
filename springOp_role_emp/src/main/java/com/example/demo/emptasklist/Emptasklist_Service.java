package com.example.demo.emptasklist;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.example.demo.tasklist.Task_Pojo;

@Service
public class Emptasklist_Service {

	@Autowired
	JdbcTemplate jtemp;
	
	@Autowired
	NamedParameterJdbcTemplate jnamedtemp;
	
	//API call for completed task and employee taskList
	String emptasklist_completed(Emptask_Pojo Emptask_pojo_obj) {
//		System.out.println("hello11 -->"+ Emptask_pojo_obj.getLogin_user_name());
		String validate = login_validation(Emptask_pojo_obj);
		String result = "";
		boolean Emp_tasklist = false;
		System.out.println(111+" ---"+validate.equals("true")+"---"+validate);
		if(validate.equals("true") == true) {
			System.out.println(1133100);
			if(Emptask_pojo_obj.getEmp_task_id() != ""){
				System.out.println(11331);
				String sql = "select * from Emp_tasklist where task_id=?";
				
				List<Map<String, Object>>sql_emp_tasklist_table=jtemp.queryForList(sql,Emptask_pojo_obj.getEmp_task_id());
				for(Map sql_emp_tasklist_obj : sql_emp_tasklist_table) {
					if(sql_emp_tasklist_obj.get("task_id").equals(Emptask_pojo_obj.getEmp_task_id())) {
						Emp_tasklist = true;
						System.out.println(111);
					}
				}
				if(Emp_tasklist == true) {
					System.out.println("entered");
					String sql_Emp_tasklist_update_status = "update table Emp_tasklist set emp_tasklist_status = F where task_id = ?";
					int result_Emp_tasklist_status = jtemp.update(sql_Emp_tasklist_update_status, Emptask_pojo_obj.getEmp_task_id());
					System.out.println("entered"+result_Emp_tasklist_status);
					String sql_task_update_status = "update table tasklist set task_status = F where taskid = ?";
					int result_task_status = jtemp.update(sql_task_update_status, Emptask_pojo_obj.getEmp_task_id());
					System.out.println("entered1   "+sql_task_update_status);
					if(result_task_status != 0 && result_Emp_tasklist_status != 0 ) {
						result = "Project Successfully completed...!";
					}else {
						result = "Project not Completed";
					}
				}
			}else {
				result = "Please enter employee task id";
				
			}
		}else {
			result = validate;
		}
		return result;
	}
	
	
	//select
	List emptasklist_select(Emptask_Pojo Emptask_pojo_obj) {
		String valiations = login_validation(Emptask_pojo_obj);
		List arraylist = new ArrayList<Object>();
		String role_id="";
		String employee = "employee";
		//successfully logged in 
		if(valiations.equals("true")) {
			
//			arraylist.add("success00---");
//			String sql_select_tasklist  = "select * from "+employee+" where 1=1 ";
			
			if(Emptask_pojo_obj.getEmp_task_status() != null) { //mptask_pojo_obj.getEmp_task_id()
				System.out.println("success1001");
				// role id from roles table
				try {
					String sql_roles = "select * from roles where role=?";
					List<Map<String,Object>> sql_roles_table = jtemp.queryForList(sql_roles,Emptask_pojo_obj.getEmp_task_status());
					for(Map sql_roles_table_obj : sql_roles_table) {
						role_id = (String) sql_roles_table_obj.get("role_id");
					}
					
				}catch(Exception e) {
					 arraylist.add("invalid role, please check role");
				}
				
				// data base on the employee status
				try {
					System.out.println("entered..???");
					
					String sql_selectdata = "SELECT *\n"
							+ "FROM Emp_tasklist\n"
							+ "INNER JOIN tasklist ON Emp_tasklist.task_id = tasklist.taskid\n"
							+ "INNER JOIN employee ON Emp_tasklist.emp_id = employee.id\n"
							+ "WHERE Emp_tasklist.emp_tasklist_status = ?";
					List<Map<String,Object>> sql_select_table = jtemp.queryForList(sql_selectdata, role_id);
					System.out.println("failed toupdate..???"+ sql_select_table);
					// if task id is incorrect
					if(!sql_select_table.isEmpty()) {
						arraylist.add(sql_select_table);
					}else {
						arraylist.add("invalid task id, please check");
					}
					
				}catch(Exception e) {
					arraylist.add("data does not exits, please check"+e);
				}
			}

			else if(Emptask_pojo_obj.getEmp_task_id() != null) {
//				System.out.println("success11");

				//for roles 
				try {
					String sql_roles = "SELECT *\n"
							+ "FROM Emp_tasklist\n"
							+ "INNER JOIN tasklist ON Emp_tasklist.task_id = tasklist.taskid\n"
							+ "INNER JOIN employee ON Emp_tasklist.emp_id = employee.id\n"
							+ "WHERE Emp_tasklist.task_id = ?";
//					System.out.println(taskpojo.getTaskstatus());
					List<Map<String, Object>> sql_roles_table = jtemp.queryForList(sql_roles,Emptask_pojo_obj.getEmp_task_id());
					if(sql_roles_table.isEmpty() == false) {
						arraylist.add(sql_roles_table);
					}else {
						// validation for invalid role 
						arraylist.add("invalid role, please check your role status should be one of as follows Completed or Assigned or Created");
					}
				}catch(Exception e) {
					arraylist.add("invalid sql error_id:12");
				}
			}
			else {
				try {
					// no id or status is given --------- or empty is given
					String no_id_or_status = "SELECT *\n"
							+ "FROM Emp_tasklist\n"
							+ "INNER JOIN tasklist ON Emp_tasklist.task_id = tasklist.taskid\n"
							+ "INNER JOIN employee ON employee.id = Emp_tasklist.emp_id;\n"
							+ "";
					List<Map<String, Object>> no_id_or_status_table = jtemp.queryForList(no_id_or_status);
					arraylist.add(no_id_or_status_table);
				}catch(Exception e) {
					arraylist.add("no data present or sql error id:emptask123");
				}				
			}
		}else {
			//failed to logged
			arraylist.add(valiations);
		}
		return arraylist;
	}
	
	//insert
	String emptasklist_insert(Emptask_Pojo Emptask_pojo_obj) {
		String result = "";
		int duration = 0;
		String pm_username="";
		boolean emp_id =false;
		boolean task_id = false;
		String tasklist_sql ="tasklist";
		String emp_project_id="";
		String login_status = login_validation(Emptask_pojo_obj);
		String sql_query_universal = "select * from ";
		if(login_status == "true") {
			
				try {
					String sql_tasklist =  sql_query_universal+ " "+tasklist_sql+" where 1=1 and taskid=?";
					List<Map<String,Object>> sql_tasklist_table = jtemp.queryForList(sql_tasklist, Emptask_pojo_obj.getEmp_task_id());
					if(sql_tasklist_table.isEmpty() == false) {
						for(Map sql_tasklist_table_obj : sql_tasklist_table) {
							
							
							if(Emptask_pojo_obj.getEmp_task_id().equals(sql_tasklist_table_obj.get("taskid"))) {
								task_id = true;
								duration = (int)sql_tasklist_table_obj.get("task_duration");
								pm_username = (String) sql_tasklist_table_obj.get("task_createdBy");
								emp_project_id = (String) sql_tasklist_table_obj.get("projectid");
								
							}
						
						}
						try {
							// validation for employee id
							String employee_table ="employee";
							String sql_emp = sql_query_universal+" "+employee_table+" where 1=1 and id=? and not role = 'P' or not role = 'H' ";
							try {
								
								List<Map<String,Object>> emp_table_data =jtemp.queryForList(sql_emp,Emptask_pojo_obj.getEmp_id());
								System.out.println("executed.. "+emp_table_data);
								if(!emp_table_data.isEmpty() == true) {
									System.out.println("eentered...");
									// duplicate value exists or not checking in the employee table 
									String sql_emptasklist_table = "select * from Emp_tasklist where task_id=? and emp_id= ?";
									List<Map<String,Object>> duplicate_exits_or_not = jtemp.queryForList(sql_emptasklist_table, Emptask_pojo_obj.getEmp_task_id(), Emptask_pojo_obj.getEmp_id());
									if(duplicate_exits_or_not.isEmpty() == false) {
										result = "Failed to insert, Task is already assigned to the employee !!!";
									}else {
										//if no duplicates exits in the Emp_tasklist then emp_id can inserted
										emp_id=true;  
									}
									
								}else {
									result = "invalid employee id, plese check ";
								}
							}catch(Exception e) {
								result = "Please enter employee id only 001";
							}
							
							
						}catch(Exception e) {
							result = "sql error 125";	
						}
						
					}else {
						result = "invalid emp_taskid";
					}
					
				}catch(Exception e) {
					result = "sql error id:123";
				}
				
				// current date format
				DateTimeFormatter current_date = DateTimeFormatter.ofPattern("dd/MM/yyyy ");
				LocalDateTime now = LocalDateTime.now();  
				String start_date = current_date.format(now);
				System.out.println(start_date);
				
				// end date calculation 
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				c.setTime(new Date()); // Using today's date
				c.add(Calendar.DATE, duration/8); // Adding 5 days
				String end_date = sdf.format(c.getTime());
				System.out.println(end_date);
//				
				if(emp_id==true && task_id==true) {
					SqlParameterSource param_insert = new MapSqlParameterSource()
							.addValue("emp_project_id",  emp_project_id)   
							.addValue("emp_task_id", Emptask_pojo_obj.getEmp_task_id())
							.addValue("pm_username", pm_username)
							.addValue("emp_id", Emptask_pojo_obj.getEmp_id())
							.addValue("start_date",start_date )
							.addValue("end_date", end_date)
							.addValue("status", "A");
					String sql_emp_task_insert = "insert into Emp_tasklist values("
							+ ":emp_project_id, :emp_task_id, :pm_username, :emp_id, :start_date, :end_date, :status)";
					int sql_results = jnamedtemp.update(sql_emp_task_insert, param_insert);
					
					String update_tasklist_status = "update tasklist set task_status = ? where taskid = ?";
					int sql_tasklist_update_status = jtemp.update(update_tasklist_status, "A", Emptask_pojo_obj.getEmp_task_id());
					//if both employee task is insert and status is updated in taskList.
					if(sql_results!=0 && sql_tasklist_update_status!=0) {
						result = "success assigned the task to the employee";
					}
				}
//				System.out.println("success");
//				result = "success";
			}else {
				result = login_status;
			}
			
		
		
		return result;
	}
	
	// login 
	public String login_validation(Emptask_Pojo Emptask_pojo_obj){
		boolean login_user = false, login_pass=false, login_condition_project_manager=false;
		String current_login_user = ""; 								
		String current_login_pass = "";										
		String role_p = "P";
		String project_manager_username = "";
//		String task_id = "";
		String result="";
		System.out.println("hello -->"+ Emptask_pojo_obj.getLogin_user_name());
		/*
		if(Emptask_pojo_obj.getEmp_task_id() != null ) {
				System.out.println(project_manager_username+"jel entered");
				
				try {
					
					//project Details table and getting project manager name
					String project_table = "select * from tasklist where taskid = ?";	
					List<Map<String, Object>> project_table_query= jtemp.queryForList(project_table, Emptask_pojo_obj.getEmp_task_id());
					System.out.println("project_table_query  ---"+project_table_query+" "+Emptask_pojo_obj.getEmp_task_id());
					if(project_table_query.isEmpty() == false) {
						
						 for(Map project_table_query_obj : project_table_query) {
								project_manager_username = (String) project_table_query_obj.get("task_createdBy");
								System.out.println(project_manager_username+"jel");
							}
						 	//validation for project manager username 
							if(project_manager_username == "") {
								result = "invalid project manager USER NAME, please check project manager user name";
								
							}
					}else {
						// validation for task id 
						 result = "invalid emp_task id, please check employee task id";	
					}
					
					
				}catch(Exception e) {
					result = "invalid task id, please check task id, sql error 124";		
				}
				
			}else {
				System.out.println(project_manager_username+"jel success ");
				result = "Please enter Task id";
				
			}
			*/
			
	
		// employee table
		String emp_sql = "Select * from employee where role = ?";
		System.out.println("hello -->"+ Emptask_pojo_obj.getLogin_user_name());
		List<Map<String, Object>> emp_table= jtemp.queryForList(emp_sql,role_p);
		System.out.println(emp_table);
		for(Map emp_table_obj : emp_table) {
			System.out.println("hello -->"+ Emptask_pojo_obj.getLogin_user_name());
			if(emp_table_obj.get("user_name").equals(Emptask_pojo_obj.getLogin_user_name())) {
				login_user = true;
				
				System.out.println(true+"username");
				current_login_user = Emptask_pojo_obj.getLogin_user_name();
			}
			if (emp_table_obj.get("user_pass").equals(Emptask_pojo_obj.getLogin_pass())) {
				login_pass = true;
				
				current_login_pass = Emptask_pojo_obj.getLogin_pass();
				System.out.println(true+"password"+current_login_pass);
			}
//			if (emp_table_obj.get("user_name").equals(project_manager_username)) {
//				login_condition_project_manager = true;
//				//project_manager = taskpojo.getProject_manager_user_name();
//				
//			}
		}	
		
		if((login_user==true && login_pass==true )  ) { //&& login_condition_project_manager==true  //|| ((login_user==true && login_pass==true) || login_condition_project_manager==true)
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
