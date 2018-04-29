package com.api.entities.business;

public class Category {
    private int id;
    private String name;

	/**
	* Default empty Category constructor
	*/
	public Category() {
		super();
	}

	/**
	* Default Category constructor
	*/
	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
}
