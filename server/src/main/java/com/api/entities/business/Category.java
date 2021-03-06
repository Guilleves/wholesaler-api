package com.api.entities.business;

import java.sql.SQLException;
import java.sql.ResultSet;

public class Category implements BaseEntity{
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
    * Default Category constructor
    */
    public Category(ResultSet rs) throws SQLException {
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
	* Create string representation of Category for printing
	* @return
	*/
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
}
