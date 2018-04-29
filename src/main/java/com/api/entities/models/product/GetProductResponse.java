package com.api.entities.models.product;

import com.api.entities.business.Category;
import com.api.entities.business.Brand;

public class GetProductResponse {
    private int id;
    private String name, gtin;
    private Brand brand;
    private Category category;

	/**
	* Default empty GetProductResponse constructor
	*/
	public GetProductResponse() {
		super();
	}

	/**
	* Default GetProductResponse constructor
	*/
	public GetProductResponse(int id, String name, String gtin, Brand brand, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.gtin = gtin;
		this.brand = brand;
		this.category = category;
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

	/**
	* Returns value of brand
	* @return
	*/
	public Brand getBrand() {
		return brand;
	}

	/**
	* Sets new value of brand
	* @param
	*/
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	/**
	* Returns value of category
	* @return
	*/
	public Category getCategory() {
		return category;
	}

	/**
	* Sets new value of category
	* @param
	*/
	public void setCategory(Category category) {
		this.category = category;
	}
}
