package com.api.entities.models.user;

public class GetUserResponse {
    private int id;
    private String firstName, lastName, username, email;

	/**
	* Default empty GetUserResponse constructor
	*/
	public GetUserResponse() {
		super();
	}

	/**
	* Default GetUserResponse constructor
	*/
	public GetUserResponse(int id, String firstName, String lastName, String username, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
	}

	/**
	* Returns value of id
	* @return
	*/
	public int getId() {
		return id;
	}

	/**
	* Sets new value of id
	* @param
	*/
	public void setId(int id) {
		this.id = id;
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
}
