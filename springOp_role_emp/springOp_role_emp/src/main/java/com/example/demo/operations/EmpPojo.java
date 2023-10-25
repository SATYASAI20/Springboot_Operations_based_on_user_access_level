package com.example.demo.operations;

import lombok.Data;

@Data
public class EmpPojo {
	
	int id;
	String user_name, user_pass, phone_no, salary, role,fname,lname,dateOfBirth;
	String login_user_name,login_pass;
	String join_date, emp_status;
	//project  part 2
	String project_desc,start_date, end_date,project_name, no_of_emp, project_manager_user_name, human_resource_user_name,projectid,emp_id;
		
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
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getEmp_status() {
		return emp_status;
	}
	public void setEmp_status(String emp_status) {
		this.emp_status = emp_status;
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
	
	
	// part one
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
	
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getUser_pass() {
		return user_pass;
	}
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
