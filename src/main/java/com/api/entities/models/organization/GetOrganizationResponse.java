package com.api.entities.models.organization;

public class GetOrganizationResponse {
    private int id;
    private String name, legalName, cuit;

	/**
	* Default empty GetOrganizationResponse constructor
	*/
	public GetOrganizationResponse() {
		super();
	}

	/**
	* Default GetOrganizationResponse constructor
	*/
	public GetOrganizationResponse(int id, String name, String legalName, String cuit) {
		super();
		this.id = id;
		this.name = name;
		this.legalName = legalName;
		this.cuit = cuit;
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
}
