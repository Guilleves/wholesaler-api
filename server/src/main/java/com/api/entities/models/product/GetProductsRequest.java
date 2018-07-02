package com.api.entities.models.product;
import com.api.entities.models.BaseSearchRequest;

public class GetProductsRequest extends BaseSearchRequest {
    static final long serialVersionUID = 0L;

    private Integer brandId, categoryId;
    private String keyword;

	/**
	* Default empty GetProductsRequest constructor
	*/
	public GetProductsRequest() {
		super();
	}

	/**
	* Default GetProductsRequest constructor
	*/
	public GetProductsRequest(Integer brandId, Integer categoryId, String keyword) {
		super();
		this.brandId = brandId;
		this.categoryId = categoryId;
		this.keyword = keyword;
	}

    /**
	* Default GetProductsRequest constructor
	*/
	public GetProductsRequest(Integer brandId, Integer categoryId, Integer pageIndex, Integer pageSize, String keyword, String orderBy) {
		super(orderBy, pageIndex, pageSize);
		this.brandId = brandId;
		this.categoryId = categoryId;
		this.keyword = keyword;
	}

	/**
	* Returns value of brandId
	* @return
	*/
	public Integer getBrandId() {
		return brandId;
	}

	/**
	* Sets new value of brandId
	* @param
	*/
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	/**
	* Returns value of categoryId
	* @return
	*/
	public Integer getCategoryId() {
		return categoryId;
	}

	/**
	* Sets new value of categoryId
	* @param
	*/
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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
}
