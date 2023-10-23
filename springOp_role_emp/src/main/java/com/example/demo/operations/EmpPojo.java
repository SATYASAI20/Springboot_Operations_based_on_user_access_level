package com.example.demo.operations;

import lombok.Data;

@Data
public class EmpPojo {
	
	int id;
	String user_name, user_pass, phone_no, salary, role,fname,lname,dateOfBirth;
	String login_user_name,login_pass;
	
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
