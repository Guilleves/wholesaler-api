package com.api.entities.business;

// #region Imports
import java.sql.ResultSet;
import java.sql.SQLException;

// #endregion

public class User {
    private int id;
    private String firstName, lastName, username, password, email;
    private Organization organization;

	/**
	* Default empty User constructor
	*/
	public User() {
		super();
	}

	/**
	* Default User constructor
	*/
	public User(int id, String firstName, String lastName, String username, String password, String email, Organization organization) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.organization = organization;
	}

    /**
	* User constructor without organization
	*/
	public User(int id, String firstName, String lastName, String username, String password, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
        this.password = password;
		this.email = email;
	}

    /**
    * SQL Constructor
    */
    public User(ResultSet rs) throws SQLException {
        super();

        this.id = rs.getInt("id");
        this.firstName = rs.getString("firstName");
		this.lastName = rs.getString("lastName");
		this.username = rs.getString("username");
		this.password = rs.getString("password");
		this.email = rs.getString("email");
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
	* Returns value of organization
	* @return
	*/
	public Organization getOrganization() {
		return organization;
	}

	/**
	* Sets new value of organization
	* @param
	*/
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	* Create string representation of User for printing
	* @return
	*/
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}
}
