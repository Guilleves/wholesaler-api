package com.api.entities.models.product;
import java.io.Serializable;

public class GetProductsRequest implements Serializable {
    static final long serialVersionUID = 0L;
    private int brandId, categoryId, pageIndex, pageSize;
    private String keyword, orderBy;

	/**
	* Default empty GetProductsByFilterRequest constructor
	*/
	public GetProductsRequest() {
		super();
	}

	/**
	* Default GetProductsByFilterRequest constructor
	*/
	public GetProductsRequest(int brandId, int categoryId, int pageIndex, int pageSize, String keyword, String orderBy) {
		super();
		this.brandId = brandId;
		this.categoryId = categoryId;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.keyword = keyword;
		this.orderBy = orderBy;
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
	* Returns value of pageIndex
	* @return
	*/
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	* Sets new value of pageIndex
	* @param
	*/
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	* Returns value of pageSize
	* @return
	*/
	public int getPageSize() {
		return pageSize;
	}

	/**
	* Sets new value of pageSize
	* @param
	*/
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	* Returns value of keyword
	* @return
	*/
	public String getKeyword() {
		return keyword;
	}

	/**
	* Sets new value of keyword
	* @param
	*/
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	* Returns value of orderBy
	* @return
	*/
	public String getOrderBy() {
		return orderBy;
	}

	/**
	* Sets new value of orderBy
	* @param
	*/
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
