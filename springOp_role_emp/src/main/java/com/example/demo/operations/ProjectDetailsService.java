package com.example.demo.operations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProjectDetailsService {
	
	@Autowired
	JdbcTemplate jtemp;
	
	String project_details(EmpPojo empObj) {
		
		String login_admin_user_name = "", login_admin_pass = "";
		String result = "";
		boolean pm=false, hr=false;
		String hr_user_name="", prject_manager_uname="";
		String project_id = "";
		//project name
		String project_name = empObj.getProject_name();
		
		// date taken 
		DateTimeFormatter date_time_format = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		LocalDateTime now = LocalDateTime.now();  
		String current_date = date_time_format.format(now);
		
		System.out.println("start");
		char[] current_date_array = current_date.toCharArray();
		
		String current_date_prject="";
		for(int i=0;i<current_date_array.length;i++) {
			System.out.println(i+" "+current_date_array[i]);
			if(i==3 || i==4 || i==8 || i==9) {
				current_date_prject += current_date_array[i];
			}
		}
		
		//project id
		project_id += project_name+current_date_prject;   		//25-10-2023 id=1023
		System.out.println(project_id);
		
		//SQL query
		String emp_sql = "select * from employee where role = ? ";
		
		List<Map<String, Object>> emp_table = jtemp.queryForList(emp_sql,"A");
		for(Map emp_table_obj : emp_table) {
			System.out.println(emp_table_obj.get("role")+"  hii"+empObj.getLogin_user_name()+" ");
			if("A".equals( emp_table_obj.get("role")) && 
					empObj.getLogin_user_name().equals(emp_table_obj.get("user_name")) 
					&& empObj.getLogin_pass().equals(emp_table_obj.get("user_pass"))) {
				
				login_admin_user_name = (String)emp_table_obj.get("user_name");
				System.out.println(login_admin_user_name+"hello----------");
				login_admin_pass = (String)emp_table_obj.get("user_pass");
				
				//assigning user role
				String assignedBy = (String) emp_table_obj.get("role");
				
				String emp_sql_role = "select * from employee ";
				
				List<Map<String, Object>> emp_table_role = jtemp.queryForList(emp_sql_role);
				
				for(Map emp_table_role_obj : emp_table_role) {
					System.out.println(emp_table_role_obj.get("user_name") +"   "+empObj.getProject_manager_user_name());
					if(emp_table_role_obj.get("role").equals("P") 
							&& emp_table_role_obj.get("user_name").equals(empObj.getProject_manager_user_name())) {
						pm=true;
						prject_manager_uname = (String)emp_table_role_obj.get("user_name");
					}
					if(emp_table_role_obj.get("role").equals("H") 
								&& emp_table_role_obj.get("user_name").equals(empObj.getHuman_resource_user_name())) {
							hr=true;
							hr_user_name=(String)emp_table_role_obj.get("user_name");
					}
					if(pm==true && hr==true) {	

							//start date
							String start_date = "";
							if(empObj.getStart_date().length()==10) {
								start_date = empObj.getStart_date();
							}else {
								result = "please enter date in dd/mm/yyyy or dd-mm-yyyy";
								break;
							}
							
							//end date
							String end_date = "";
							if(empObj.getEnd_date().length()==10) {
								start_date = empObj.getEnd_date();
							}else {
								result = "please enter date in dd/mm/yyyy or dd-mm-yyyy";
								break;
							}
							
							try {
								//project details sql queary
								String project_sql = "insert into projectDetails values(?,?,?,?,?,?,?,?,?)";
								System.out.println(start_date+" "+end_date);
								int project_table = jtemp.update(project_sql, project_id, project_name, empObj.getProject_desc()				
												,start_date , end_date, 
												empObj.getProject_manager_user_name(), empObj.getHuman_resource_user_name(), 
												assignedBy, empObj.getNo_of_emp());
								if(project_table != 0) {
									result = "successfully inserted the Project Details";
									break;
								}else {
									result = "Failed to insert the Project Details";
									break;
								}
							}catch(Exception e) {
								result = "Duplicate entry, please check your details";
							}
					}
				}
				break;
			}else {
				result = "Please enter a valid User Name and Password, only Admin can login insert data";
			}
		}
		if(hr_user_name == "") {
			result = "invalid hr user name";
		}
		if(prject_manager_uname == "") {
			result = "invalid project manager user name";
		}
		if(login_admin_user_name == "") {
			result = "invalid login user name ! only admin can login";
			
		}
		if(login_admin_pass == "") {
			result = "invalid LOGIN user name or PASSWORD ! only admin can login";
		}
		return result;
	}
}



