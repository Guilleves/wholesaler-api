package com.api.entities.models.user;

// #region Imports

import java.io.Serializable;

// #endregion

public class SaveUserRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private int userId;
    private String username, firstName, lastName, email, password, repeatPassword;

	/**
	* Default empty SaveUserRequest constructor
	*/
	public SaveUserRequest() {
		super();
	}

	/**
	* Default SaveUserRequest constructor
	*/
	public SaveUserRequest(int userId, String username, String firstName, String lastName, String email, String password, String repeatPassword) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.repeatPassword = repeatPassword;
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

	/**
	* Returns value of firstName
	* @return
	*/
	public String getFirstName() {
		return firstName;
	}

	/**
	* Sets new value of firstName
	* @param
	*/
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	* Returns value of lastName
	* @return
	*/
	public String getLastName() {
		return lastName;
	}

	/**
	* Sets new value of lastName
	* @param
	*/
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	* Returns value of email
	* @return
	*/
	public String getEmail() {
		return email;
	}

	/**
	* Sets new value of email
	* @param
	*/
	public void setEmail(String email) {
		this.email = email;
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

	/**
	* Returns value of repeatPassword
	* @return
	*/
	public String getRepeatPassword() {
		return repeatPassword;
	}

	/**
	* Sets new value of repeatPassword
	* @param
	*/
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
}
