package com.api.entities.business;

// #region Imports

import java.sql.ResultSet;
import java.sql.SQLException;

// #endregion

public class Organization {
    private int id;
    private String name, cuit, legalName, role;

	/**
	* Default empty Organization constructor
	*/
	public Organization() {
		super();
	}

	/**
	* Default Organization constructor
	*/
	public Organization(int id, String name, String cuit, String legalName, String role) {
		super();
		this.id = id;
		this.name = name;
		this.cuit = cuit;
		this.legalName = legalName;
		this.role = role;
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
	* Returns value of name
	* @return
	*/
	public String getName() {
		return name;
	}

	/**
	* Sets new value of name
	* @param
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	* Returns value of cuit
	* @return
	*/
	public String getCuit() {
		return cuit;
	}

	/**
	* Sets new value of cuit
	* @param
	*/
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	/**
	* Returns value of legalName
	* @return
	*/
	public String getLegalName() {
		return legalName;
	}

	/**
	* Sets new value of legalName
	* @param
	*/
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	/**
	* Returns value of role
	* @return
	*/
	public String getRole() {
		return role;
	}

	/**
	* Sets new value of role
	* @param
	*/
	public void setRole(String role) {
		this.role = role;
	}
}
