package com.api.entities.models.proposal;

import java.util.Date;

public class GetProposalsResponse {
    private int id;
    private String title, description, status;
    private Date beginDate, endDate;

	/**
	* Default empty GetProposalResponse constructor
	*/
	public GetProposalsResponse() {
		super();
	}

	/**
	* Default GetProposalResponse constructor
	*/
	public GetProposalsResponse(int id, String title, String description, String status, Date beginDate, Date endDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
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
	* Returns value of title
	* @return
	*/
	public String getTitle() {
		return title;
	}

	/**
	* Sets new value of title
	* @param
	*/
	public void setTitle(String title) {
		this.title = title;
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
