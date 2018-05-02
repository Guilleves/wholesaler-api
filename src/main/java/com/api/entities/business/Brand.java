package com.api.entities.business;

public class Brand {
    private int id;
    private String name;

	/**
	* Default empty Brand constructor
	*/
	public Brand() {
		super();
	}

	/**
	* Default Brand constructor
	*/
	public Brand(int id, String name) {
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

	/**
	* Create string representation of Brand for printing
	* @return
	*/
	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + "]";
	}
}
