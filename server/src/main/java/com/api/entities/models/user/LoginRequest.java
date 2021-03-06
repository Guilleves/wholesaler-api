package com.api.entities.models.user;

// #region Imports

import java.io.Serializable;

// #endregion

public class LoginRequest implements Serializable {
    static final long serialVersionUID = 0L;
    private String username, password;

	/**
	* Default empty LoginRequest constructor
	*/
	public LoginRequest() {
		super();
	}

	/**
	* Default LoginRequest constructor
	*/
	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/**
	* Returns value of username
	* @return
	*/
	public String getUsername() {
		return username;
	}

	/**
	* Sets new value of username
	* @param
	*/
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	* Returns value of password
	* @return
	*/
	public String getPassword() {
		return password;
	}

	/**
	* Sets new value of password
	* @param
	*/
	public void setPassword(String password) {
		this.password = password;
	}
}
