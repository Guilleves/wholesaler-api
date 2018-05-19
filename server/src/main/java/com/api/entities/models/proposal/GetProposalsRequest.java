package com.api.entities.models.proposal;

public class GetProposalsRequest {
    private String orderBy, status;
    private Integer pageIndex, pageSize, supplierId;

	/**
	* Default empty GetProposalsRequest constructor
	*/
	public GetProposalsRequest() {
		super();
	}

	/**
	* Default GetProposalsRequest constructor
	*/
	public GetProposalsRequest(String orderBy, String status, Integer pageIndex, Integer pageSize, Integer supplierId) {
		super();
		this.orderBy = orderBy;
		this.status = status;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.supplierId = supplierId;
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
	* Returns value of status
	* @return
	*/
	public String getStatus() {
		return status;
	}

	/**
	* Sets new value of status
	* @param
	*/
	public void setStatus(String status) {
		this.status = status;
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

	/**
	* Returns value of supplierId
	* @return
	*/
	public Integer getSupplierId() {
		return supplierId;
	}

	/**
	* Sets new value of supplierId
	* @param
	*/
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
}
