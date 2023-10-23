package com.example.demo.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;


@Service
public class service {

	@Autowired
	NamedParameterJdbcTemplate jtemp;
	
	@Autowired
	JdbcTemplate Jtemp;
	
	String insert(pojo pojo_obj){
		String loginuser_name = pojo_obj.getLogin_user_name();
		String login_user_pass = pojo_obj.getLogin_pass();
//		int result=0;
//		String sql_role = "select * from roles where role_Id = ? and role_Id = H or role_Id = A ";
		String sql_role = "select * from employee where user_name = ? and user_pass = ? ";
		String role="";
		List<Map<String, Object>> login_emp_data = Jtemp.queryForList(sql_role, loginuser_name, login_user_pass ); //-------
		System.out.println("executed query"+sql_role);
		String result="";
		for(Map login_user : login_emp_data) {
//			i1.get("role");
			System.out.println("entere. login");
			
			if(login_user.get("role").equals("H") || login_user.get("role").equals("A")) {
					System.out.println("entered.. login");
					String fname = pojo_obj.getFname();
//					System.out.println(fname);
					String lname = pojo_obj.getLname();
					String dateOfBirth = pojo_obj.getDateOfBirth();
					
					//email creation start
					String email_fchar = Character.toString(fname.charAt(0)).toLowerCase();
				    String email_lchar = lname.toLowerCase();
				    String generated_email = email_fchar+email_lchar+"@miraclesoft.com";
				    //email creation end
				    
				    
					
					 
					  
					 //user_name creation start
					 char user_name_fchar = fname.charAt(0);
					 String user_name_lchar = lname;
					 char[] dob = dateOfBirth.toCharArray();					//2023-10-25  //27-10-2002
					 String  user_dateOfBirth_Y="", user_dateOfBirth_M="", user_dateOfBirth_D="";
					 
					 for(int i=0;i<dob.length;i++) {
						 
						 if(i==2 || i==3) {
							 user_dateOfBirth_D += Character.toString(dob[i]);
						 }else if(i==5 || i==6) {
							 user_dateOfBirth_M+=Character.toString(dob[i]);
						 }else if(i==8 || i==9) {
							 user_dateOfBirth_Y+=Character.toString(dob[i]);
						 }
					 }
					 String gen_user_name = user_name_fchar+user_name_lchar+"@"+user_dateOfBirth_D+user_dateOfBirth_M+user_dateOfBirth_Y;
					 //user_name creation end
					 
					 
					 // user_password creation start 
					 java.time.format.DateTimeFormatter date = java.time.format.DateTimeFormatter.ofPattern("HHmm");
					 java.time.LocalDateTime currentTime = java.time.LocalDateTime.now();
					 String time_pass = date.format(currentTime);
					 String gen_user_password = fname.substring(0, 1).toUpperCase()+lname.substring(0,3)+"@"+time_pass;
					// user_password creation end
					 
					String sql_roles_table = "select * from roles ";
					List<Map<String, Object>> al_roles = Jtemp.queryForList(sql_roles_table);
					for(Map roles_object : al_roles) {
						
						if(roles_object.get("role").equals(pojo_obj.getRole())) {
							String role_id_match = (String) roles_object.get("role_Id");
							List isel_data = new ArrayList<>();
							
							 SqlParameterSource p = new MapSqlParameterSource()
									 	.addValue("id",pojo_obj.getId())
									 	.addValue("user_name", gen_user_name) //generate
										.addValue("user_pass", gen_user_password)  //generate
										.addValue("salary", pojo_obj.getSalary())
										.addValue("phone_no", pojo_obj.getPhone_no())
										.addValue("role",role_id_match)
										.addValue("fname", pojo_obj.getFname())
										.addValue("lname", pojo_obj.getLname())
										.addValue("dateOfBirth", pojo_obj.getDateOfBirth())
										.addValue("email", generated_email); //generate 
								
							 
							 
							 String insert_data = "insert into employee values(:id,:user_name,:user_pass,:phone_no,:salary,:role,:fname,:lname,:dateOfBirth,:email)";	
							 System.out.println(".. hello");
							 
							try {
								int update_results = jtemp.update(insert_data,p);
								System.out.println("jedsasdf");
								if(update_results != 0) {
								result = "success";
								}
							}catch(Exception e) {
								result = e+"check sql error";
							}
							break;
//									result = "Successfully inserted the data";
						}else {
							result = "invalid role please check details";
						}
					}
			}else {
				result = "only hr or Admin can insert data";
			}
		
		}
		
		return result;
	}
	
}


