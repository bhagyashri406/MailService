package com.socialmedia.mailservice.model;

public class NotificationDTO {

	private String event;
	private int Userid;
	private String Username;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getUserid() {
		return Userid;
	}

	public void setUserid(int userid) {
		Userid = userid;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		this.Username = username;
	}

	public NotificationDTO() {
		super();
	}

	public NotificationDTO(String event, int userid, String username) {
		super();
		this.event = event;
		this.Userid = userid;
		this.Username = username;
	}

}
