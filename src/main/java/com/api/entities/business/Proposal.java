package com.api.entities.business;

import java.util.ArrayList;
import java.util.Date;

public class Proposal {
    private int id;
    private Date beginDate, endDate;
    private String title, description;
    private ArrayList<ProposalLine> proposalLines;
    private Supplier supplier;

	/**
	* Default empty Proposal constructor
	*/
	public Proposal() {
		super();
	}

	/**
	* Default Proposal constructor
	*/
	public Proposal(int id, Date beginDate, Date endDate, String title, String description, ArrayList<ProposalLine> proposalLines, Supplier supplier) {
		super();
		this.id = id;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.title = title;
		this.description = description;
		this.proposalLines = proposalLines;
		this.supplier = supplier;
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
	* Returns value of proposalLines
	* @return
	*/
	public ArrayList<ProposalLine> getProposalLines() {
		return proposalLines;
	}

	/**
	* Sets new value of proposalLines
	* @param
	*/
	public void setProposalLines(ArrayList<ProposalLine> proposalLines) {
		this.proposalLines = proposalLines;
	}

	/**
	* Returns value of supplier
	* @return
	*/
	public Supplier getSupplier() {
		return supplier;
	}

	/**
	* Sets new value of supplier
	* @param
	*/
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

    /**
	* Create string representation of Proposal for printing
	* @return
	*/
	@Override
	public String toString() {
		String string = "Proposal [id=" + id + ", title=" + title.toString() + ", beginDate=" + beginDate.toString() + ", endDate=" + endDate.toString() + ", description=" + description + "] ";

        string += "Lines [ ";
        for(ProposalLine pl : proposalLines) {
            string = string + pl.toString();
        }
        string += "]";

        return string;
	}
}
