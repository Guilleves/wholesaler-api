package com.api.entities.models.user;

public class LoginResponse {
    private String token;

	/**
	* Default empty LoginResponse constructor
	*/
	public LoginResponse() {
		super();
	}

	/**
	* Default LoginResponse constructor
	*/
	public LoginResponse(String token) {
		super();
		this.token = token;
	}

	/**
	* Returns value of token
	* @return
	*/
	public String getToken() {
		return token;
	}

	/**
	* Sets new value of token
	* @param
	*/
	public void setToken(String token) {
		this.token = token;
	}
}
