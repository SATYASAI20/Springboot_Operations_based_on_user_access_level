package com.example.demo.project_emp_meetings;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.example.demo.emptasklist.Emptask_Pojo;

@Service
public class Project_Emp_Service {

	@Autowired
	JdbcTemplate jtemp;
	
	@Autowired
	NamedParameterJdbcTemplate Jtemp;
	
	public String project_emp_meetings(Meetings_Pojo Project_Emp_Pojo) {
		boolean date_validation=false;
		String result = "";
		String validate_status = login_validation(Project_Emp_Pojo);
		String[] validate_status_array = login_validation(Project_Emp_Pojo).split(" ");
		String validate_status_true = "", validate_status_login_user=" ", validate_failed_status ="", validate_status_login_user_role="";
		
		String meeting_id = "";
		String meeting_name = Project_Emp_Pojo.getMeeting_name();
		System.out.println(meeting_name+"_____________-->");
		String meeting_start_date = "";
		int duration = Project_Emp_Pojo.getMeeting_duration();
		String end_date = "";
		String end_time = "";
//		String end_date_time1 = "";
		
		// getting user name from the login 
		int validate_status_obj_count = 0;
		for(String validate_status_obj : validate_status_array) {
			if(validate_status_obj_count==0) {
				if(validate_status_obj.equals("true")) {
					validate_status_true = validate_status_obj;
					
				}else {
					validate_failed_status = login_validation(Project_Emp_Pojo);
				}
			}else if(validate_status_obj_count == 1){
				validate_status_login_user =  validate_status_obj;
				System.out.println(validate_status_obj+"----loginuser----<3>");
			}else if(validate_status_obj_count == 2){
				validate_status_login_user_role =  validate_status_obj;
				System.out.println(validate_status_obj+"--role------<4>");
			}else {
				result = "error validation id:'pro_emp_service:1'";
			}
			validate_status_obj_count+=1;
		}
		
		// login success 
		if(validate_status_true.equals("true")){
			 
			if(meeting_name != null || meeting_name != "") {
				
				// current date format 
				DateTimeFormatter current_date_format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDateTime now = LocalDateTime.now();  
				String current_date = current_date_format.format(now);
				char[] current_date_charArray = current_date.toCharArray();
				String current_date_for_id = "";
				for(int i=0;i<current_date_charArray.length;i++){
					if(i==0 || i==1 || i==3|| i==4 || i==8 || i==9) {
						current_date_for_id += Character.toString(current_date_charArray[i]);
					}
				}
				//meeting id generated 
				meeting_id = meeting_name+"@"+current_date_for_id;
				System.out.println("start");
				if(Project_Emp_Pojo.getStart_date() != null) {
					
					if(Project_Emp_Pojo.getStart_time() != null) {
						
						meeting_start_date = Project_Emp_Pojo.getStart_date();
						
						// end date calculation 
						//current time
						DateTimeFormatter da = DateTimeFormatter.ofPattern("HH:mm");
						LocalTime date1_time = LocalTime.parse(Project_Emp_Pojo.getStart_time());
						String current_time = da.format(date1_time);
						System.out.println(current_time+"-------??");
						
						//end date and time calculation using duration
						DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
						System.out.println("ji");
						LocalDateTime date_obj = LocalDateTime.parse(meeting_start_date+" "+current_time,date);
						LocalDateTime end_date_time=date_obj.plusMinutes(duration);
						System.out.println(end_date_time+"current time");
						String end_date_time_1 =end_date_time.toString();
						String[] end_date_time_2 = end_date_time_1.split("T");
						int i=0;
						for(String s_obj : end_date_time_2) {
							System.out.println(s_obj);
							if(i==0) {
								end_date = s_obj;
							}else if(i==1) {
								end_time = s_obj;
							}
							i+=1;
						}
						date_validation = true;
						System.out.println(end_date);
						System.out.println(end_time);
					}else {
						result = "please enter start time..!";
					}
					
				}
				else if(meeting_start_date.equals("")){
					
					//start date 
					DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/YYYY");
					LocalDateTime current_start_date = LocalDateTime.now();
					String start_date = date.format(current_start_date);
					System.out.println(start_date+"------*");
					
					// end date calculation 
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					Calendar c = Calendar.getInstance();
//					System.out.println(c+"current time");
					c.setTime(new Date()); // Using today's date
					c.add(Calendar.MINUTE, duration);  
					end_date = sdf.format(c.getTime());
					
					String[] s = end_date.split(" ");
					int i=0;
					for(String s_obj : s) {
						System.out.println(s_obj);
						if(i==0) {
							end_date = s_obj;
						}else if(i==1) {
							end_time = s_obj;
						}
						i+=1;
					}
					date_validation = true;
					System.out.println(end_date);
					System.out.println(end_time);
					
				}
				else {
					result = "invalid start date id:'pro_emp_service:1', please check..! ";
				}
				
				// above date validation is true 
				if(date_validation = true) {
					String project_manager = "", hr="";
					String employee_content_ids = "";
					boolean meeting_status = false;
					String meeting_created_user ="";
					// projectDetails
					String sql_project_details = "select * from projectDetails where projectid=? ";
					List<Map<String, Object>> sql_project_details_table = jtemp.queryForList(sql_project_details,Project_Emp_Pojo.getProjectid());
					for(Map sql_project_details_table_obj : sql_project_details_table) {
						project_manager = (String)sql_project_details_table_obj.get("PM");
						hr = (String)sql_project_details_table_obj.get("HR");
						System.out.println(project_manager+"-->"+hr+"--->"+validate_status_login_user+"--->"+validate_status_login_user_role);
					}
					// only other employees can invited to meeting whose role is employee
					if(validate_status_login_user_role.equals("E")) {
						String sql_pro_emp_details = "select * from projectEmp_Details where project_id = ? ";
						List<Map<String,Object>> sql_pro_emp_details_table = jtemp.queryForList(sql_pro_emp_details, Project_Emp_Pojo.getProjectid());
						for(Map sql_pro_emp_details_table_obj : sql_pro_emp_details_table) {
							String emp_id_exits = (String)sql_pro_emp_details_table_obj.get("emp_id");
							// meeting employee not id's given
							if(Project_Emp_Pojo.getMeeting_content()==null) {
								// checks employee role is employee(E) or not if role is 'E' then add to string, if not it will skip
								// so that employee can invite other employees
								String sql_employee_id = "select * from employee where id=? and role=?";
								List<Map<String,Object>> sql_employee_id_table_role = jtemp.queryForList(sql_employee_id, emp_id_exits,"E");
								if(sql_employee_id_table_role.isEmpty()==false) {
									employee_content_ids += (String)sql_pro_emp_details_table_obj.get("emp_id")+" ";
								}
							}else {
								//meeting id's given
								if(Project_Emp_Pojo.getMeeting_content()!=null || Project_Emp_Pojo.getMeeting_content()!=" ") {
									String[] employee_ids = Project_Emp_Pojo.getMeeting_content().split(" ");
									
									for(String employee_ids_obj : employee_ids) {
										if(emp_id_exits.equals((String)employee_ids_obj)) {
											employee_content_ids += (String)sql_pro_emp_details_table_obj.get("emp_id")+" ";
										}
									}
									
								}else {
									result = "enter a valid employee ids, error id : 'pro_emp_service:3' ";
									break;
								}
							}
							
						}
						SqlParameterSource param = new MapSqlParameterSource()
								.addValue("projectid", Project_Emp_Pojo.getProjectid())
								.addValue("meeting_id", meeting_id)
								.addValue("meeting_name", Project_Emp_Pojo.getMeeting_name())
								.addValue("employee_content_ids", employee_content_ids)
								.addValue("start_time", Project_Emp_Pojo.getStart_time())
								.addValue("end_time", end_time)
								.addValue("meeting_duration", Project_Emp_Pojo.getMeeting_duration())
								.addValue("meeting_status", "C")
								.addValue("createdBy", validate_status_login_user)
								.addValue("meeting_desc", Project_Emp_Pojo.getMeeting_desc())
								.addValue("start_date", Project_Emp_Pojo.getStart_date())
								.addValue("end_date", end_date);
						try {
							
							//validating meeting already created or not
							String sql_meeting_status = "select * from meetings where meeting_name = ? and meeting_status = ?";
							List<Map<String,Object>> sql_meetings_status_table = jtemp.queryForList(sql_meeting_status, Project_Emp_Pojo.getMeeting_name(), "C");
							System.out.println(sql_meetings_status_table+"-------");
							if(!sql_meetings_status_table.isEmpty()) {
								for(Map sql_meetings_status_table_obj :sql_meetings_status_table) {
									if(sql_meetings_status_table_obj.get("meeting_status").equals("C")) {
										meeting_created_user = (String)sql_meetings_status_table_obj.get("createdBy");
										result = "Meeting is already created by USER NAME -!->"+ meeting_created_user;
										break;
										//// some ----------
									
									}
								}
							}else {
								meeting_status = true;
							}
							
							//insert data into meetings table 
							String sql_insert_meetings = "insert into meetings values(:projectid,:meeting_id,"
									+ ":meeting_name,:employee_content_ids,:start_time,:end_time,:meeting_duration,:meeting_status,"
									+ ":createdBy,:meeting_desc,:start_date,:end_date)";
							int insert_success = Jtemp.update(sql_insert_meetings, param);
							
							
							if(insert_success != 0 && meeting_status == true) {
								result = "successfully created the meeting for employee by USER NAME --->"+validate_status_login_user+" added users -->"+employee_content_ids;
							}else {
								result = "meeting is already created by, please check once USER NAME --->"+meeting_created_user;
							}
						}catch(Exception e) {
							result = "sql error id:'pro_emp_service:1'";
						}
						
					}
					// HR or Project Manager has access to invite all team and HR.
					else if(validate_status_login_user_role.equals("H") || validate_status_login_user_role.equals("P")){
						String sql_pro_emp_details = "select * from projectEmp_Details where project_id = ? ";
						List<Map<String,Object>> sql_pro_emp_details_table = jtemp.queryForList(sql_pro_emp_details, Project_Emp_Pojo.getProjectid());
						for(Map sql_pro_emp_details_table_obj : sql_pro_emp_details_table) {
							employee_content_ids += (String)sql_pro_emp_details_table_obj.get("emp_id")+" ";
						}
						SqlParameterSource param = new MapSqlParameterSource()
								.addValue("projectid", Project_Emp_Pojo.getProjectid())
								.addValue("meeting_id", meeting_id)
								.addValue("meeting_name", Project_Emp_Pojo.getMeeting_name())
								.addValue("employee_content_ids", employee_content_ids)
								.addValue("start_time", Project_Emp_Pojo.getStart_time())
								.addValue("end_time", end_time)
								.addValue("meeting_duration", Project_Emp_Pojo.getMeeting_duration())
								.addValue("meeting_status", "C")
								.addValue("createdBy", validate_status_login_user)
								.addValue("meeting_desc", Project_Emp_Pojo.getMeeting_desc())
								.addValue("start_date", Project_Emp_Pojo.getStart_date())
								.addValue("end_date", end_date);
						try {
							
							//validating meeting already created or not
							String sql_meeting_status = "select * from meetings where meeting_name = ? and meeting_status = ?";
							List<Map<String,Object>> sql_meetings_status_table = jtemp.queryForList(sql_meeting_status, Project_Emp_Pojo.getMeeting_name(), "C");
							for(Map sql_meetings_status_table_obj :sql_meetings_status_table) {
								if(sql_meetings_status_table_obj.get("meeting_status").equals("C")) {
									meeting_created_user = (String)sql_meetings_status_table_obj.get("createdBy");
									result = "Meeting is already created by USER NAME -->"+ meeting_created_user;
								}else {
									meeting_status = true;
								}
							}
						
							//insert data into meetings table 
							String sql_insert_meetings = "insert into meetings values(:projectid,:meeting_id,"
									+ ":meeting_name,:employee_content_ids,:start_time,:end_time,:meeting_duration,:meeting_status,"
									+ ":createdBy,:meeting_desc,:start_date,:end_date)";
							int insert_success = Jtemp.update(sql_insert_meetings, param);
							
							
							if(insert_success != 0 && meeting_status == true) {
								result = "successfully created the meeting for employee by USER NAME --->"+validate_status_login_user;
							}else {
								result = "meeting is already created by, please check once USER NAME --->"+meeting_created_user;
							}
						}catch(Exception e) {
							result = "sql error id:'pro_emp_service:2'";
						}
					}
					
				}
			}
			
			
			
//			
//			
		}else {
			result = validate_status;
		}
		return result;
		
	}
	
	// login 
	public String login_validation(Meetings_Pojo Project_Emp_Pojo){
			boolean login_user = false, login_pass=false, login_emp=false;
			String current_login_user = ""; 								
			String current_login_pass = "";		
			String current_emp_role="";
//			String[] emp_id = new String[] {};
			List<String> emp_id_array = new ArrayList<>();
			
//			String role_p = "P";
//			String project_manager_username = "";
//			String task_id = "";
			String result="";
//			System.out.println("hello -->");
			
			//
			if(Project_Emp_Pojo.getProjectid() != null) {
				//here validation of respective project employee, base on project id getting emp_id 
				//and comparing with employee table employee id
				String project_emp_details = "select * from projectEmp_Details where project_id=?";
				List<Map<String, Object>> project_emp_details_table = jtemp.queryForList(project_emp_details,Project_Emp_Pojo.getProjectid());
				if(project_emp_details_table.isEmpty() == true) {
					result = "project id, does't exits. please check...!";
				}
				for(Map project_emp_details_table_obj : project_emp_details_table) {
					if(project_emp_details_table_obj.get("project_id").equals(Project_Emp_Pojo.getProjectid())){
						String emp = (String)project_emp_details_table_obj.get("emp_id");
						emp_id_array.add(emp);
					}else {
						result = "invlaid project id, please check";
					}
				}
			}
			
			// employee table
			String emp_sql = "Select * from employee";
//			System.out.println("hello -->"+ Project_Emp_Pojo.getLogin_user_name());
			List<Map<String, Object>> emp_table= jtemp.queryForList(emp_sql);
//			System.out.println(emp_table);
			for(Map emp_table_obj : emp_table) {
//				System.out.println("hello -->"+ Project_Emp_Pojo.getLogin_user_name());
				if(emp_table_obj.get("user_name").equals(Project_Emp_Pojo.getLogin_user_name())) {
					login_user = true;
					
//					System.out.println(true+"username");
					current_login_user = Project_Emp_Pojo.getLogin_user_name();
					System.out.println(current_login_user+"--------<>");
				}
				if (emp_table_obj.get("user_pass").equals(Project_Emp_Pojo.getLogin_pass())) {
					login_pass = true;
					
					current_login_pass = Project_Emp_Pojo.getLogin_pass();
//					System.out.println(true+"password"+current_login_pass);
				}
				
				for(String emp : emp_id_array) {
//					System.out.println("done----!");
					
					if(emp.equals(String.valueOf((int)emp_table_obj.get("id")))) {
//						System.out.println("done----?");
						login_emp = true;
						// getting role from employee table
						current_emp_role = (String)emp_table_obj.get("role");
					}
				}
			}	
			
			
			
			
			if((login_user==true && login_pass==true && login_emp == true)  ) { //&& login_condition_project_manager==true  //|| ((login_user==true && login_pass==true) || login_condition_project_manager==true)
				//login status , employee role and employee role stored in result  
				result = "true"+" "+current_login_user+" "+current_emp_role;
				System.out.println(current_login_user+"--------<2>");
			}
			//login validation username
			if(current_login_user=="") {
				result = "invalid user name, please check your user name";
									
			}
			//login password validate
			if(current_login_pass == "") {
				result = "invalid PASSWORD, please check your PASSWORD";	
			}
			
			if(login_emp == false) {
				result = "only respective project employees can login, please check your project id..!";
			}
			System.out.println(emp_id_array);
			return result;
		}

}
