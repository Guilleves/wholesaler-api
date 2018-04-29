package com.api.entities.business;

// #region Imports

import java.util.ArrayList;

// #endregion

public class Resource {
  private int id;
  private String resourceName;
  private ArrayList<Permission> permissions;

	/**
	* Default empty Resource constructor
	*/
	public Resource() {
		super();
	}

	/**
	* Default Resource constructor
	*/
	public Resource(int id, String resourceName) {
		super();
		this.id = id;
		this.resourceName = resourceName;
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
	* Returns value of resourceName
	* @return
	*/
	public String getResourceName() {
		return resourceName;
	}

	/**
	* Sets new value of resourceName
	* @param
	*/
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

  public ArrayList<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(ArrayList<Permission> permissions) {
    this.permissions = permissions;
  }

  public void addPermission(Permission permission) {
    if (permissions == null)
      permissions = new ArrayList<Permission>();

    permissions.add(permission);
  }
}
