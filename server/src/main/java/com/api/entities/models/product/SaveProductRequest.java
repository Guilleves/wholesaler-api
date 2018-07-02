package com.api.entities.models.product;

import java.io.Serializable;

public class SaveProductRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private int id, brandId, categoryId;
    private String name, gtin, description;

	/**
	* Default empty SaveProductRequest constructor
	*/
	public SaveProductRequest() {
		super();
	}

	/**
	* Default SaveProductRequest constructor
	*/
	public SaveProductRequest(int id, int brandId, int categoryId, String name, String gtin, String description) {
		super();
		this.id = id;
		this.brandId = brandId;
		this.categoryId = categoryId;
		this.name = name;
    this.gtin = gtin;
		this.description = description;
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
	* Returns value of brandId
	* @return
	*/
	public int getBrandId() {
		return brandId;
	}

	/**
	* Sets new value of brandId
	* @param
	*/
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	/**
	* Returns value of categoryId
	* @return
	*/
	public int getCategoryId() {
		return categoryId;
	}

	/**
	* Sets new value of categoryId
	* @param
	*/
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
	* Returns value of gtin
	* @return
	*/
	public String getGtin() {
		return gtin;
	}

	/**
	* Sets new value of gtin
	* @param
	*/
	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

  public String getDescription(){
    return description;
  }

  public void setDescription(String description){
    this.description = description;
  }
}
