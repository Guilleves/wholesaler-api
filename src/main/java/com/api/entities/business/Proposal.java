package com.api.entities.business;

import java.util.ArrayList;
import java.util.Date;

public class Proposal {
    private int id;
    private Date beginDate, endDate;
    private String description;
    private ArrayList<ProposalLine> proposalLines;

	/**
	* Default empty Proposal constructor
	*/
	public Proposal() {
		super();

        proposalLines = new ArrayList<ProposalLine>();
	}

	/**
	* Default Proposal constructor
	*/
	public Proposal(int id, Date beginDate, Date endDate, String description, ArrayList<ProposalLine> proposalLines) {
		super();
		this.id = id;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.description = description;
		this.proposalLines = proposalLines;
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
	* Returns value of proposalLine
	* @return
	*/
	public ArrayList<ProposalLine> getProposalLines() {
		return proposalLines;
	}

	/**
	* Sets new value of proposalLine
	* @param
	*/
	public void setProposalLines(ArrayList<ProposalLine> proposalLines) {
		this.proposalLines = proposalLines;
	}
}
