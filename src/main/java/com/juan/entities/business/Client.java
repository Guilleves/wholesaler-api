package com.api.entities.business;

// #region Imports

import java.sql.ResultSet;
import java.sql.SQLException;

// #endregion

public class Client {
  private int id;
  private String firstName, lastName, fullName,
    primaryAddress1, primaryAddress2, primaryPhone,
    city, province;

  	/**
  	* Default empty Client constructor
  	*/
  	public Client() {
  		super();
  	}

  	/**
  	* Default Client constructor
  	*/
  	public Client(int id, String firstName, String lastName, String primaryAddress1, String primaryAddress2, String primaryPhone, String city, String province) {
  		super();
  		this.id = id;
  		this.firstName = firstName;
  		this.lastName = lastName;
  		this.primaryAddress1 = primaryAddress1;
  		this.primaryAddress2 = primaryAddress2;
  		this.primaryPhone = primaryPhone;
  		this.city = city;
  		this.province = province;
  	}

    /**
    * Client constructor for SQL
    */
    public Client(ResultSet rs) {
  		super();
      try {
        this.id = rs.getInt("id");
        this.firstName = rs.getString("firstName");
        this.lastName = rs.getString("lastName");
        this.primaryAddress1 = rs.getString("primaryAddress1");
        this.primaryAddress2 = rs.getString("primaryAddress2");
        this.primaryPhone = rs.getString("primaryPhone");
        this.city = rs.getString("city");
        this.province = rs.getString("province");
      }
      catch(SQLException e) {

      }
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
	* Returns value of fullName
	* @return
	*/
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	* Returns value of primaryAddress1
	* @return
	*/
	public String getPrimaryAddress1() {
		return primaryAddress1;
	}

	/**
	* Sets new value of primaryAddress1
	* @param
	*/
	public void setPrimaryAddress1(String primaryAddress1) {
		this.primaryAddress1 = primaryAddress1;
	}

	/**
	* Returns value of primaryAddress2
	* @return
	*/
	public String getPrimaryAddress2() {
		return primaryAddress2;
	}

	/**
	* Sets new value of primaryAddress2
	* @param
	*/
	public void setPrimaryAddress2(String primaryAddress2) {
		this.primaryAddress2 = primaryAddress2;
	}

	/**
	* Returns value of primaryPhone
	* @return
	*/
	public String getPrimaryPhone() {
		return primaryPhone;
	}

	/**
	* Sets new value of primaryPhone
	* @param
	*/
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	/**
	* Returns value of city
	* @return
	*/
	public String getCity() {
		return city;
	}

	/**
	* Sets new value of city
	* @param
	*/
	public void setCity(String city) {
		this.city = city;
	}

	/**
	* Returns value of province
	* @return
	*/
	public String getProvince() {
		return province;
	}

	/**
	* Sets new value of province
	* @param
	*/
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	* Create string representation of Client for printing
	* @return
	*/
	@Override
	public String toString() {
		return "Client [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", fullName=" + fullName + ", primaryAddress1=" + primaryAddress1 + ", primaryAddress2=" + primaryAddress2 + ", primaryPhone=" + primaryPhone + ", city=" + city + ", province=" + province + "]";
	}
}
