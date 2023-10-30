package com.example.demo.operations.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;


import com.example.demo.operations.pojo.ProjectEmp_insert_pojo;

@Service
public class ProjectEmpDetails_Service {
	
	@Autowired
	JdbcTemplate jtemp;
	
	@Autowired
	NamedParameterJdbcTemplate jnamed_temp;
	
	public String emp_details(ProjectEmp_insert_pojo empObj){
//		System.out.println("enterd method");
		boolean login_user = false, login_pass=false, login_condition_project_manager=false;
		String result = "";
		String current_login_user = ""; 								//empObj.getLogin_user_name();
		String current_login_pass = "";										//empObj.getLogin_pass();
		String role_p = "P";
		String project_id = " ";
		String project_manager = "";
//		boolean pm=false, projectid=false;
		String project_details = "select * from projectDetails "; 
		List<Map<String, Object>> project_details_table = jtemp.queryForList(project_details);
		
//		System.out.println("query executed");
		for(Map project_details_obj : project_details_table) {	
			
//			System.out.println("forloop 1");
//			System.out.println(project_details_obj.get("PM")+"  "+(empObj.getProject_manager_user_name()));
//			System.out.println(project_details_obj.get("projectid")+" "+empObj.getProjectid());
			
			if(project_details_obj.get("PM").equals(empObj.getProject_manager_user_name())
					&& project_details_obj.get("projectid").equals(empObj.getProjectid())) {
				
				// no of employees
				int no_employee_required_for_project = (int)project_details_obj.get("no_of_emp");
//				System.out.println("Pm and id entered");
				
				//project id
				String projectid = empObj.getProjectid();
//				System.out.println(projectid+" satya hello");
				
				//project manager
//				String project_manager = "";			//empObj.getProject_manager_user_name();
				
				//project start date
				String project_start_date = (String)project_details_obj.get("start_date");
				
				//project_manager_user_name
				String project_manager_user_name = (String)project_details_obj.get("PM");
				
				// employee table
				String emp_sql = "Select * from employee where role = '"+role_p+"'";
				
				List<Map<String, Object>> emp_table= jtemp.queryForList(emp_sql);
				
//				System.out.println("query executed");
				
				for(Map emp_table_obj : emp_table) {
					
					if(emp_table_obj.get("user_name").equals(empObj.getLogin_user_name())) {
						login_user = true;
						current_login_user = empObj.getLogin_user_name();
					}
					if (emp_table_obj.get("user_pass").equals(empObj.getLogin_pass())) {
						login_pass = true;
						current_login_pass = empObj.getLogin_pass();
					}
					if (emp_table_obj.get("user_name").equals(empObj.getProject_manager_user_name())) {
						login_condition_project_manager = true;
						project_manager = empObj.getProject_manager_user_name();
						
					}
					
					if(login_user == true && login_pass == true && login_condition_project_manager== true) {	
						//----changed 
//						System.out.println("query executed ..."+emp_table_obj.get("id")+" "+(empObj.getEmp_id()));
//						System.out.println("query executed ..."+emp_table_obj.get("id")+" "+(empObj.getEmp_id()));
						//employee table
						String for_pro_emp_sql = "Select * from employee where id=?";
						
						List<Map<String, Object>> for_pro_emp_sql_table = jtemp.queryForList(for_pro_emp_sql,empObj.getEmp_id());
//						System.out.println(for_pro_emp_sql_table);
//						System.out.println("query executed !!!");
						if(!for_pro_emp_sql_table.isEmpty()) {
							for(Map  for_pro_emp_sql_obj: for_pro_emp_sql_table) {  //-----
//							System.out.println(for_pro_emp_sql_obj.get("id")+" "+((String)empObj.getEmp_id()));
							
//								System.out.println("condition satisified !!!");
							
							
							
								//employee count validation --------------
								String emp_count = "select * from projectEmp_Details where project_id = ?";
								List<Map<String, Object>> project_emp_requirement_table = jtemp.queryForList(emp_count,(String)project_details_obj.get("projectid"));
								
								//project id validation
								System.out.println(project_emp_requirement_table.size() +"-----------");
								
								if(!project_emp_requirement_table.isEmpty()) {
									for(Map project_emp_requirement_table_obj : project_emp_requirement_table) {
										project_id = (String)project_emp_requirement_table_obj.get("project_id");
									} 
								}
								
								if(no_employee_required_for_project>project_emp_requirement_table.size() ) {
										System.out.println(project_emp_requirement_table.size()+"hello tharun"+ no_employee_required_for_project);
											
											//employee id
											String emp_id = empObj.getEmp_id();
											String project_name = (String)project_details_obj.get("project_name");
											String project_emp_name = (String)for_pro_emp_sql_obj.get("lname")+" "+(String)emp_table_obj.get("fname");
											String project_emp_phone_no = (String)for_pro_emp_sql_obj.get("phone_no");
											String project_emp_email = (String)for_pro_emp_sql_obj.get("email");
											String project_emp_join_date = (String) for_pro_emp_sql_obj.get("join_date");
											
											
											// project id validation
											
											
											SqlParameterSource param = new MapSqlParameterSource()
													.addValue("emp_id", emp_id)
													.addValue("projectid", projectid)
													.addValue("project_name", project_name)
													.addValue("PM", project_manager)
													.addValue("project_emp_name", project_emp_name)
													.addValue("project_emp_phone_no", project_emp_phone_no)
													.addValue("project_emp_email", project_emp_email)
													.addValue("project_emp_join_date", project_emp_join_date)
													.addValue("project_start_date", project_start_date)
													.addValue("project_manager_user_name", project_manager_user_name);
											
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
													try {													
														int success = jnamed_temp.update(project_emp_details,param);
//														System.out.println("success project_emp_details !!!"+success);
														
														if(success != 0) {
															try {
																String update_status = "update employee set emp_status='A' where id=?";
																int updated_status = jtemp.update(update_status,emp_id);
																if(updated_status != 0) { 
																	result = " Employee successfully inserted into project";
																	break;
																}
															}catch(Exception e) {
																result="duplicate entry, please check";
															}	
														}else {
															result = "failed to insert data ";
														}
													}catch(Exception e) {
														result = "failed to insert data, duplicate entry "; //please check employee id, "project name and phone number";
													}
											}
											else {
												result = "more than employee requirement for the project";
											}
											
								
											
								break;
								}
						}else {
							result = "invalid employee id, Please check the employee id !!!";
						}
						System.out.println(project_id+"hello");
						if(project_id.equals(" ")) {
							result = "invalid project id please check project id that your entered";
						}
						break;
					}
				}
				
				if(current_login_user=="") {
					result = "invalid user name, please check your user name";
					break;
				}
				if(current_login_pass == "") {
					result = "invalid PASSWORD, please check your PASSWORD";
					break;
				}
//				if(project_manager == "") {
//					result = "invalid project manager USER NAME, please check project manager user name";
//					break;
//				}
			}
		}
		if(project_manager == "") {
			result = "invalid project manager USER NAME, please check project manager user name";
			
		}
		if(project_id == " ") {
			result = "invalid project id , please check project id";
			
		}
		
		return result;
	}
}
