package com.example.demo.operations.service;

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

import com.example.demo.operations.pojo.New_ProjectOp_Pojo;

@Service
public class New_ProjectService {
	
	@Autowired
	JdbcTemplate jtemp;
	
	@Autowired
	NamedParameterJdbcTemplate jnamed_temp;
	
	public String newprojectOp(New_ProjectOp_Pojo new_project_obj) {
		// new project name
		String newproject_name = new_project_obj.getNew_project_name();
		String oldproject_name = new_project_obj.getOld_project_name();
//		System.out.println(new_project_obj.getOld_project_name()+" hello");
		String current_login_user = "";
		String current_login_user_pass = "";
		String new_project_id="";
		String result = "";
		boolean current_login_user_name=false, current_login_user_password=false,role=false;
//		String hr_user_name="", prject_manager_uname="";
				//SQL query
				String emp_sql = "select * from employee where role = ? ";
				
				List<Map<String, Object>> emp_table = jtemp.queryForList(emp_sql,"A");
				for(Map emp_table_obj : emp_table) {
					
//					System.out.println(emp_table_obj.get("role")+"  hii"+new_project_obj.getLogin_user_name()+" ");
					
					//admin validation
					if("A".equals( emp_table_obj.get("role"))) {
						role=true;
					}
					//login user name
					if(new_project_obj.getLogin_user_name().equals(emp_table_obj.get("user_name"))) {
						current_login_user = new_project_obj.getLogin_user_name();
						current_login_user_name = true;
					}
					//login user password
					if( new_project_obj.getLogin_pass().equals(emp_table_obj.get("user_pass"))) {
						current_login_user_pass = new_project_obj.getLogin_pass();
						current_login_user_password = true;
					}
					// validation user login 
					if(role==true && current_login_user_name==true && current_login_user_name== true) {
						
//						System.out.println("validated");
						
						// old project employee data
						String old_project_sql = "select * from projectDetails where project_name = ?";
						List<Map<String, Object>> old_project_table = jtemp.queryForList(old_project_sql, oldproject_name);
						if(old_project_table.isEmpty() == false){
							
						
							System.out.println("validated old_project table executed"+old_project_table);
							for(Map old_project_table_obj : old_project_table) {
							
								if(old_project_table_obj.get("project_name").equals(oldproject_name)) {
								
//									System.out.println("validated project name");
									// date taken 
									DateTimeFormatter date_time_format = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
									LocalDateTime now = LocalDateTime.now();  
									String current_date = date_time_format.format(now);
								
									System.out.println("start");
									char[] current_date_array = current_date.toCharArray();
								
									String current_date_project="";
									for(int i=0;i<current_date_array.length;i++) {
										System.out.println(i+" "+current_date_array[i]);
										if(i==3 || i==4 || i==8 || i==9) {
											current_date_project += current_date_array[i];
										}
									}
									//project id
									new_project_id += newproject_name+current_date_project;   		//25-10-2023 id=1023
									System.out.println(new_project_id);
								
								
									//project desc
									String project_desc = (String)old_project_table_obj.get("project_desc");
								
									// start date
									String project_start_date = (String)old_project_table_obj.get("start_date");
								
									//end date
									String project_end_date = (String)old_project_table_obj.get("end_date");
								
									//Project Manager
									String PM = (String)old_project_table_obj.get("PM");
								
									// HR
									String HR = (String)old_project_table_obj.get("HR");
								
									//AssignedBy
									String assignedBy = (String)old_project_table_obj.get("assignedBy");
								
									// Number of employee
									int no_of_emp = (int)old_project_table_obj.get("no_of_emp");
//									System.out.println("validated sql try--------");
									try {
										System.out.println("validated sql try block");
										//project details sql queary
										String project_sql = "insert into projectDetails values(?,?,?,?,?,?,?,?,?)";
//										System.out.println(start_date+" "+end_date);
										int project_table = jtemp.update(project_sql, new_project_id, newproject_name, project_desc				
													,project_start_date , project_end_date, 
													PM, HR, 
													assignedBy, no_of_emp );
										System.out.println("validated query executed");
										if(project_table != 0) {
										
											//copying the employee data from old to new project
											copy_emp_to_new_project(new_project_obj,oldproject_name,newproject_name, new_project_id );
										
										
											result ="successfully insert the employee data  ";
											break;
										}else {
											result = "Failed to copy Project Details into new Project";
											break;
										}
									}catch(Exception e) {
										result = "Duplicate entry, please check your details";
									}							
								}				
							}		
						}else {
							return "invalid project name, please check the project name!!";
						}
					}else {
						result = "Please enter a valid User Name and Password, only Admin can login insert data";
					}
				}
				if(current_login_user == "") {
					result = "invalid login user name";
				}
				if(current_login_user_pass=="") {
					result = "invalid login user password";
				}
				if(role == false) {
					result = "invalid role, please check your role";
				}
				
		return result;
	}
	
	
	// copy_emp_to_new_project method calling for employee data assigning to new project 
	String copy_emp_to_new_project(New_ProjectOp_Pojo copy_project_obj, String oldproject_name, String newproject_name, String new_project_id ) {
		System.out.println("enterd the copy_emp_to_new_project");
		String result = "";
		int success =0;
		//employee table
		String for_pro_emp_sql = "Select * from projectEmp_Details where project_name = ?";
		//oldproject name
		String copy_oldproject_name = oldproject_name;
		// new project name
		String copy_newproject_name = newproject_name;
		// copy new project id
		String copy_new_project_id = new_project_id;
		// emp_id
		String emp_id="";
		List<Map<String, Object>> copy_pro_emp_sql_table = jtemp.queryForList(for_pro_emp_sql, copy_oldproject_name);
//		System.out.println(for_pro_emp_sql_table);
//		System.out.println("query executed !!!");
		int kl = 0;
		for(Map  copy_pro_emp_sql_obj: copy_pro_emp_sql_table) {
			
			 System.out.println("condition satisified-- hello copy--- !!!");

//			System.out.println(copy_pro_emp_sql_obj+"   i");
			System.out.println((kl+=1)+"------------klasdjf------------------");
//				System.out.println("condition satisified !!!");
				//employee id
				emp_id = (String) copy_pro_emp_sql_obj.get("emp_id");
//				String project_name = (String)copy_pro_emp_sql_obj.get("project_name");
				String project_manager = (String)copy_pro_emp_sql_obj.get("PM");
				String project_emp_name = (String)copy_pro_emp_sql_obj.get("emp_name");
				String project_emp_phone_no = (String)copy_pro_emp_sql_obj.get("emp_phone");
				String project_emp_email = (String)copy_pro_emp_sql_obj.get("emp_email");
				String project_emp_join_date = (String)copy_pro_emp_sql_obj.get("emp_join_date");
				String project_start_date = (String)copy_pro_emp_sql_obj.get("emp_project_start_date");
				String pm_user_name_assignedBy = (String)copy_pro_emp_sql_obj.get("assignedBy");
//				System.out.println(emp_id+" "+project_name+" "+project_emp_join_date);
//				System.out.println(project_emp_name+" "+project_emp_phone_no+" "+project_emp_email);
				
				SqlParameterSource param = new MapSqlParameterSource()
						.addValue("emp_id", emp_id)
						.addValue("projectid", copy_new_project_id)
						.addValue("project_name", copy_newproject_name)
						.addValue("PM", project_manager)
						.addValue("project_emp_name", project_emp_name)
						.addValue("project_emp_phone_no", project_emp_phone_no)
						.addValue("project_emp_email", project_emp_email)
						.addValue("project_emp_join_date", project_emp_join_date)
						.addValue("project_start_date", project_start_date)
						.addValue("project_manager_user_name", pm_user_name_assignedBy);
				
				String project_emp_details = "insert into projectEmp_Details values(:emp_id,:projectid,"
						+ ":project_name,"
						+ ":PM,"
						+ ":project_emp_name,"
						+ ":project_emp_phone_no,"
						+ ":project_emp_email,"
						+ ":project_emp_join_date,"
						+ ":project_start_date,"
						+ ":project_manager_user_name"
						+ ")";
				System.out.println("enter project emp details sql");
//				System.out.println();
				System.out.println(emp_id+" 0"+copy_new_project_id+"--"+copy_newproject_name+"--"+project_manager);
				System.out.println("--------------------------------------------------");
				System.out.println(project_emp_name+" 0"+project_emp_phone_no+"--"+project_emp_email+"--"+project_emp_join_date
						+"=="+project_start_date+"--"+pm_user_name_assignedBy);
				System.out.println("--------------------------------------------------");
//				int success =0;
//						try {
							System.out.println(success);
							 success = jnamed_temp.update(project_emp_details, param);
//							 System.out.println(success);
							System.out.println("success project_emp_details !!!"+success+" "+emp_id);
							
							if(success > 0) {
								result = "successfully insert the employee data  "+emp_id;
//								try {
//									String update_status = "update employee set emp_status='A' where id=?";
//									int updated_status = jtemp.update(update_status,emp_id);
//							
//									if(updated_status != 0) { 
//										result = " Employee successfully inserted into project";
//										break;
//									}
//								}catch(Exception e) {
//									result="duplicate entry, please check";
//								}	
							}else {
								result = "failed to insert data ";
							}
//						}catch(Exception e) {
//							result = "failed to insert data, duplicate entry "; //please check employee id, "project name and phone number";
//						}
//				break; //-----
			
			
		}
		return result;
	}
//	else {  ///--------------
//		result = "invalid user name or password or project manager user name, please check";
//	}

	
}
