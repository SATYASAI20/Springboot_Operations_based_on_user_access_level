package com.example.demo.tasklist;

public class Task_Pojo {
	String  task_name, description, duration,projectid;
	
	String taskid, taskrole;
	
	public String getTaskrole() {
		return taskrole;
	}

	public void setTaskrole(String taskrole) {
		this.taskrole = taskrole;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	//-------------------------------------------
	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

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

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
