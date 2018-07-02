package com.api.entities.business;

import java.sql.SQLException;
import java.sql.ResultSet;
import com.api.entities.enums.ProposalStates;
import java.util.ArrayList;
import java.util.Date;

public class Proposal implements BaseEntity {
    private int id;
    private Date beginDate, endDate, deletedAt;
    private String title, description;
    private ArrayList<ProposalLine> proposalLines;
    private Supplier supplier;


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
	public Proposal(int id, Date beginDate, Date endDate, String title, String description, ArrayList<ProposalLine> proposalLines, Supplier supplier) {
		super();
        proposalLines = new ArrayList<ProposalLine>();

		this.id = id;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.title = title;
		this.description = description;
		this.proposalLines = proposalLines;
		this.supplier = supplier;
	}

    /**
    * Default Proposal constructor
    */
    public Proposal(ResultSet rs) throws SQLException {
        super();

        this.id = rs.getInt("id");
        this.beginDate = rs.getTimestamp("beginDate");
        this.endDate = rs.getTimestamp("endDate");
        this.title = rs.getString("title");
        this.description = rs.getString("description");

        this.supplier = new Supplier(
            rs.getInt("organizationId"),
            rs.getString("organizationName"),
            rs.getString("cuit"),
            rs.getString("legalName"),
            rs.getString("role")
        );
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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date date) {
        this.deletedAt = date;
    }

    public boolean isActive() {
        Date today = new Date();

        return endDate.after(today) && (beginDate.equals(today) || beginDate.before(today));
    }

    public String getStatus() {
        Date today = new Date();

        if (beginDate == null || beginDate == null)
            return null;

        if (beginDate.after(today))
            return ProposalStates.SCHEDULED;
        else if (endDate.after(today) && (beginDate.equals(today) || beginDate.before(today)))
            return ProposalStates.ACTIVE;
        else if (endDate.before(today))
            return ProposalStates.FINISHED;
        else
            return null;
    }

	/**
	* Create string representation of Proposal for printing
	* @return
	*/
	@Override
	public String toString() {
		String string = "Proposal [id=" + id + ", title=" + title + ", beginDate=" + beginDate + ", endDate=" + endDate + ", description=" + description + "] ";

        string += "Lines [ ";
        for(ProposalLine pl : proposalLines) {
            string = string + pl.toString();
        }
        string += "]";

        return string;
	}
}
