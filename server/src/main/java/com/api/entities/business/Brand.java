package com.api.entities.business;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Brand implements BaseEntity{
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
	* Default Brand constructor
	*/
	public Brand(ResultSet rs) throws SQLException {
		super();
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
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
