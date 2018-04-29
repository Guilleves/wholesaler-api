package com.api.entities.business;

public class ResourcePermission {
  private int id;
  private Resource resource;
  private Permission permission;

	/**
	* Default empty ResourcePermission constructor
	*/
	public ResourcePermission() {
		super();
	}

	/**
	* Default ResourcePermission constructor
	*/
	public ResourcePermission(int id, Resource resource, Permission permission) {
		super();
		this.id = id;
		this.resource = resource;
		this.permission = permission;
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
	* Returns value of resource
	* @return
	*/
	public Resource getResource() {
		return resource;
	}

	/**
	* Sets new value of resource
	* @param
	*/
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/**
	* Returns value of permission
	* @return
	*/
	public Permission getPermission() {
		return permission;
	}

	/**
	* Sets new value of permission
	* @param
	*/
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
}
