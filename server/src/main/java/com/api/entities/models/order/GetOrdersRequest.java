package com.api.entities.models.order;

import com.api.entities.models.BaseSearchRequest;
import java.util.Date;

public class GetOrdersRequest extends BaseSearchRequest {
    static final long serialVersionUID = 0L;

    private Integer retailId;
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
	public GetOrdersRequest(Integer retailId, Date fromDate, Date toDate) {
		super();
		this.retailId = retailId;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

    /**
	* Default GetOrdersRequest constructor
	*/
	public GetOrdersRequest(String orderBy, Integer pageIndex, Integer pageSize, Integer retailId, Date fromDate, Date toDate) {
		super(orderBy, pageIndex, pageSize);
		this.retailId = retailId;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	/**
	* Returns value of retailId
	* @return
	*/
	public Integer getRetailId() {
		return retailId;
	}

	/**
	* Sets new value of retailId
	* @param
	*/
	public void setRetailId(Integer retailId) {
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
