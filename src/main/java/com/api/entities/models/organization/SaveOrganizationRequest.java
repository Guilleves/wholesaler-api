package com.api.entities.models.organization;

import java.io.Serializable;

public class SaveOrganizationRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private int id;
    private String legalName, cuit, name, role;

	/**
	* Default empty SaveOrganizationRequest constructor
	*/
	public SaveOrganizationRequest() {
		super();
	}

	/**
	* Default SaveOrganizationRequest constructor
	*/
	public SaveOrganizationRequest(int id, String legalName, String cuit, String name, String role) {
		super();
		this.id = id;
		this.legalName = legalName;
		this.cuit = cuit;
		this.name = name;
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
