package com.example.demo.operations.pojo;

public class ProjectEmp_insert_pojo {
	
	String login_user_name,login_pass; //----
	// project part 3 
//	String old_project_name,new_project_name; 
	
	String emp_id, projectid,project_manager_user_name;
	
	public String getProject_manager_user_name() {
		return project_manager_user_name;
	}
	public void setProject_manager_user_name(String project_manager_user_name) {
		this.project_manager_user_name = project_manager_user_name;
	}
	
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	
	
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	
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
}

