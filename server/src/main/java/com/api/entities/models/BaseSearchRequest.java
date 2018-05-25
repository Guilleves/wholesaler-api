package com.api.entities.models;

import java.io.Serializable;

public class BaseSearchRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private String orderBy;
    private Integer pageIndex, pageSize;

	/**
	* Default empty BaseSearchRequest constructor
	*/
	public BaseSearchRequest() {
		super();
	}

	/**
	* Default BaseSearchRequest constructor
	*/
	public BaseSearchRequest(String orderBy, Integer pageIndex, Integer pageSize) {
		super();
		this.orderBy = orderBy;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
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

	/**
	* Returns value of pageIndex
	* @return
	*/
	public Integer getPageIndex() {
		return pageIndex;
	}

	/**
	* Sets new value of pageIndex
	* @param
	*/
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	* Returns value of pageSize
	* @return
	*/
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	* Sets new value of pageSize
	* @param
	*/
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
