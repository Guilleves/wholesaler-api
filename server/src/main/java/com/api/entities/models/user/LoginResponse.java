package com.api.entities.models.user;

import com.api.entities.models.organization.GetOrganizationResponse;

public class LoginResponse {
    private String token;
    private GetUserResponse user;
    private GetOrganizationResponse organization;

	/**
	* Default empty LoginResponse constructor
	*/
	public LoginResponse() {
		super();
	}

	/**
	* Default LoginResponse constructor
	*/
	public LoginResponse(String token, GetUserResponse user, GetOrganizationResponse organization) {
		super();
		this.token = token;
		this.user = user;
		this.organization = organization;
	}

	/**
	* Returns value of token
	* @return
	*/
	public String getToken() {
		return token;
	}

	/**
	* Sets new value of token
	* @param
	*/
	public void setToken(String token) {
		this.token = token;
	}

	/**
	* Returns value of user
	* @return
	*/
	public GetUserResponse getUser() {
		return user;
	}

	/**
	* Sets new value of user
	* @param
	*/
	public void setUser(GetUserResponse user) {
		this.user = user;
	}

	/**
	* Returns value of organization
	* @return
	*/
	public GetOrganizationResponse getOrganization() {
		return organization;
	}

	/**
	* Sets new value of organization
	* @param
	*/
	public void setOrganization(GetOrganizationResponse organization) {
		this.organization = organization;
	}
}
