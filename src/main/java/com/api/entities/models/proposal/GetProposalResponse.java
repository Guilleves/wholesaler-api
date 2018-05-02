package com.api.entities.models.proposal;

import java.util.Date;

public class GetProposalResponse {
    private int id;
    private String description;
    private Date beginDate, endDate;

	/**
	* Default empty GetProposalResponse constructor
	*/
	public GetProposalResponse() {
		super();
	}

	/**
	* Default GetProposalResponse constructor
	*/
	public GetProposalResponse(int id, String description, Date beginDate, Date endDate) {
		super();
		this.id = id;
		this.description = description;
		this.beginDate = beginDate;
		this.endDate = endDate;
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
	* Returns value of description
	* @return
	*/
	public String getDescription() {
		return description;
	}

	/**
	* Sets new value of description
	* @param
	*/
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	* Returns value of beginDate
	* @return
	*/
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	* Sets new value of beginDate
	* @param
	*/
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	* Returns value of endDate
	* @return
	*/
	public Date getEndDate() {
		return endDate;
	}

	/**
	* Sets new value of endDate
	* @param
	*/
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
