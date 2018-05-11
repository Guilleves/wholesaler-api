package com.api.entities.models.organization;

// #region Imports

import java.io.Serializable;

// #endregion

public class GetOrganizationsRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private String role;

	/**
	* Default empty GetOrganizationsRequest constructor
	*/
	public GetOrganizationsRequest() {
		super();
	}

	/**
	* Default GetOrganizationsRequest constructor
	*/
	public GetOrganizationsRequest(String role) {
		super();
		this.role = role;
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
