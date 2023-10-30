package com.example.demo.operations.pojo;

public class Project_Details_Pojo {

	//part 2 admin login 
//		String user_name, user_pass;
		String login_user_name,login_pass;
		//project  part 2
		String project_name,project_desc, start_date, end_date,human_resource_user_name,no_of_emp;
		String project_manager_user_name;//-------------------------------
			
		public String getLogin_user_name() {
			return login_user_name;
		}
		public void setLogin_user_name(String login_user_name) {
			this.login_user_name = login_user_name;
		}
		
		public String getLogin_pass() {
			return login_pass;
		}
		public void setLogin_pass(String login_pass) {
			this.login_pass = login_pass;
		}
		
		public String getProject_name() {
			return project_name;
		}
		public void setProject_name(String project_name) {
			this.project_name = project_name;
		}
		
		public String getProject_desc() {
			return project_desc;
		}
		public void setProject_desc(String project_desc) {
			this.project_desc = project_desc;
		}
		
		public String getStart_date() {
			return start_date;
		}
		public void setStart_date(String start_date) {
			this.start_date = start_date;
		}
		
		public String getEnd_date() {
			return end_date;
		}
		public void setEnd_date(String end_date) {
			this.end_date = end_date;
		}
		
		public String getProject_manager_user_name() {
			return project_manager_user_name;
		}
		public void setProject_manager_user_name(String project_manager_user_name) {
			this.project_manager_user_name = project_manager_user_name;
		}
		
		public String getHuman_resource_user_name() {
			return human_resource_user_name;
		}
		public void setHuman_resource_user_name(String human_resource_user_name) {
			this.human_resource_user_name = human_resource_user_name;
		}
		
		public String getNo_of_emp() {
			return no_of_emp;
		}
		public void setNo_of_emp(String no_of_emp) {
			this.no_of_emp = no_of_emp;
		}
		
		
}
