package com.example.demo.project_emp_meetings;



public class Meetings_Pojo {
	
	private String projectid, meeting_name, meeting_content;
	private String start_time, start_date, meeting_desc;
	private String login_user_name, login_pass;
	private int meeting_duration;
	
	
	
	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
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

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getMeeting_name() {
		return meeting_name;
	}

	public void setMeeting_name(String meeting_name) {
		this.meeting_name = meeting_name;
	}

	public String getMeeting_content() {
		return meeting_content;
	}

	public void setMeeting_content(String meeting_content) {
		this.meeting_content = meeting_content;
	}

	
	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

//	public String getStart_date() {
//		return start_date;
//	}
//
//	public void setStart_date(String start_date) {
//		this.start_date = start_date;
//	}

	public int getMeeting_duration() {
		return meeting_duration;
	}

	public void setMeeting_duration(int meeting_duration) {
		this.meeting_duration = meeting_duration;
	}

	public String getMeeting_desc() {
		return meeting_desc;
	}

	public void setMeeting_desc(String meeting_desc) {
		this.meeting_desc = meeting_desc;
	}


}
