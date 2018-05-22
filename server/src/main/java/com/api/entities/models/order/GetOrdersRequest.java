package com.api.entities.models.order;

import java.util.Date;

public class GetOrdersRequest {
    private String orderBy;
    private int pageIndex, pageSize, retailId;
    private Date fromDate, toDate;

	/**
	* Default empty GetOrdersRequest constructor
	*/
	public GetOrdersRequest() {
		super();
	}

	/**
	* Default GetOrdersRequest constructor
	*/
	public GetOrdersRequest(String orderBy, int pageIndex, int pageSize, int retailId, Date fromDate, Date toDate) {
		super();
		this.orderBy = orderBy;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.retailId = retailId;
		this.fromDate = fromDate;
		this.toDate = toDate;
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
	* Returns value of retailId
	* @return
	*/
	public int getRetailId() {
		return retailId;
	}

	/**
	* Sets new value of retailId
	* @param
	*/
	public void setRetailId(int retailId) {
		this.retailId = retailId;
	}

	/**
	* Returns value of fromDate
	* @return
	*/
	public Date getFromDate() {
		return fromDate;
	}

	/**
	* Sets new value of fromDate
	* @param
	*/
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	* Returns value of toDate
	* @return
	*/
	public Date getToDate() {
		return toDate;
	}

	/**
	* Sets new value of toDate
	* @param
	*/
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
