package com.api.entities.models.proposal;

import com.api.entities.models.BaseSearchRequest;

public class GetProposalsRequest extends BaseSearchRequest {
    static final long serialVersionUID = 0L;
    private String status;
    private Integer supplierId;

	/**
	* Default empty GetProposalsRequest constructor
	*/
	public GetProposalsRequest() {
		super();
	}

	/**
	* Default GetProposalsRequest constructor
	*/
	public GetProposalsRequest(String status, Integer supplierId) {
		super();
		this.status = status;
		this.supplierId = supplierId;
	}

    public GetProposalsRequest(String orderBy, String status, Integer pageIndex, Integer pageSize, Integer supplierId) {
        super(orderBy, pageIndex, pageSize);
        this.status = status;
        this.supplierId = supplierId;
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
