package com.api.entities.business;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product implements BaseEntity{
    private int id;
    private String name, gtin;
    private Brand brand;
    private Category category;
    private Date deletedAt;

	/**
	* Default empty Product constructor
	*/
	public Product() {
		super();
	}

	/**
	* Default Product constructor
	*/
	public Product(int id, String name, String gtin, Brand brand, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.gtin = gtin;
		this.brand = brand;
		this.category = category;
	}

    /**
    * Default Product constructor
    */
    public Product(ResultSet rs) throws SQLException {
        super();

        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.gtin = rs.getString("gtin");
        this.brand = new Brand(
            rs.getInt("brandId"),
            rs.getString("brandName")
        );
        this.category = new Category(
            rs.getInt("categoryId"),
            rs.getString("categoryName")
        );
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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date date) {
        this.deletedAt = date;
    }

	/**
	* Create string representation of Product for printing
	* @return
	*/
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", gtin=" + gtin + ", brand=" + brand.toString() + ", category=" + category.toString() + "]";
	}
}
