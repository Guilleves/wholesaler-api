package com.api.entities.models.user;

// #region Imports

import java.io.Serializable;

// #endregion

public class GetUserRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private int userId;
    private String username;

	/**
	* Default empty GetUserRequest constructor
	*/
	public GetUserRequest() {
		super();
	}

	/**
	* Default GetUserRequest constructor
	*/
	public GetUserRequest(int userId, String username) {
		super();
		this.userId = userId;
		this.username = username;
	}

	/**
	* Returns value of userId
	* @return
	*/
	public int getUserId() {
		return userId;
	}

	/**
	* Sets new value of userId
	* @param
	*/
	public void setUserId(int userId) {
		this.userId = userId;
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
}
