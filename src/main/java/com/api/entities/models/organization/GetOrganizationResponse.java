package com.api.entities.models.organization;

import java.util.Objects;

public class GetOrganizationResponse {
    private int id;
    private String name, legalName, cuit, role;

	/**
	* Default empty GetOrganizationResponse constructor
	*/
	public GetOrganizationResponse() {
		super();
	}

	/**
	* Default GetOrganizationResponse constructor
	*/
	public GetOrganizationResponse(int id, String name, String legalName, String cuit, String role) {
		super();
		this.id = id;
		this.name = name;
		this.legalName = legalName;
		this.cuit = cuit;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof GetOrganizationResponse)) {
            return false;
        }
        GetOrganizationResponse organization = (GetOrganizationResponse) o;
        return (id == organization.id &&
                Objects.equals(name, organization.name) &&
                Objects.equals(cuit, organization.cuit) &&
                Objects.equals(legalName, organization.legalName) &&
                Objects.equals(role, organization.role));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cuit, legalName, role);
    }
}
