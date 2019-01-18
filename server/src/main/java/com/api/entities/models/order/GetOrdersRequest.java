package com.api.entities.models.order;

import com.api.entities.models.BaseSearchRequest;
import java.util.Date;

public class GetOrdersRequest extends BaseSearchRequest {
    static final long serialVersionUID = 0L;

    private Integer retailId, proposalId, supplierId;
    private Date fromDate, toDate;
    private boolean showDeleted;

	/**
	* Default empty GetOrdersRequest constructor
	*/
	public GetOrdersRequest() {
		super();
	}

	/**
	* Default GetOrdersRequest constructor
	*/
	public GetOrdersRequest(Integer retailId, Integer proposalId, Integer supplierId, Date fromDate, Date toDate, boolean showDeleted) {
		super();
		this.retailId = retailId;
    this.proposalId = proposalId;
		this.fromDate = fromDate;
		this.toDate = toDate;
    this.supplierId = supplierId;
    this.showDeleted = showDeleted;
	}

    /**
	* Default GetOrdersRequest constructor
	*/
	public GetOrdersRequest(String orderBy, Integer pageIndex, Integer pageSize, Integer retailId, Integer proposalId, Integer supplierId, Date fromDate, Date toDate, boolean showDeleted) {
		super(orderBy, pageIndex, pageSize);
		this.retailId = retailId;
    this.proposalId = proposalId;
		this.fromDate = fromDate;
    this.supplierId = supplierId;
		this.toDate = toDate;
    this.showDeleted = showDeleted;
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
	* Returns value of proposalId
	* @return
	*/
	public Integer getProposalId() {
		return proposalId;
	}

	/**
	* Sets new value of proposalId
	* @param
	*/
	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
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

	/**
	* Returns value of showDeleted
	* @return
	*/
	public boolean isShowDeleted() {
		return showDeleted;
	}

	/**
	* Sets new value of showDeleted
	* @param
	*/
	public void setShowDeleted(boolean showDeleted) {
		this.showDeleted = showDeleted;
	}
}
