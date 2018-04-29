package com.api.entities.business;

public class Permission {
  private int id;
  private String permissionName;

	/**
	* Default empty Permission constructor
	*/
	public Permission() {
		super();
	}

	/**
	* Default Permission constructor
	*/
	public Permission(int id, String permissionName) {
		super();
		this.id = id;
		this.permissionName = permissionName;
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
	* Returns value of permissionName
	* @return
	*/
	public String getPermissionName() {
		return permissionName;
	}

	/**
	* Sets new value of permissionName
	* @param
	*/
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
}
