package com.etms.worldline.payload.request;



public class LoginRequest {
	private Long user_id;

	public Long getUser_id() {
		return user_id;
	}



	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	private String username;

	private String email;

	private String password;

	private String user_fcm_token;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getUser_fcm_token() {
		return user_fcm_token;
	}

	public void setUser_fcm_token(String user_fcm_token) {
		this.user_fcm_token = user_fcm_token;
	}
}
