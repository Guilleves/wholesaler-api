package com.api.entities.models.organization;

// #region Imports

import java.io.Serializable;

// #endregion

public class GetOrganizationRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private int organizationId;

	/**
	* Default empty GetOrganizationRequest constructor
	*/
	public GetOrganizationRequest() {
		super();
	}

	/**
	* Default GetOrganizationRequest constructor
	*/
	public GetOrganizationRequest(int organizationId) {
		super();
		this.organizationId = organizationId;
	}

	/**
	* Returns value of organizationId
	* @return
	*/
	public int getOrganizationId() {
		return organizationId;
	}

	/**
	* Sets new value of organizationId
	* @param
	*/
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
}
