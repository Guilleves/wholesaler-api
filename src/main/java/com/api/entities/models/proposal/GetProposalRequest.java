package com.api.entities.models.proposal;

// #region Imports

import java.io.Serializable;

// #endregion

public class GetProposalRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private int proposalId;

	/**
	* Default empty GetProposalRequest constructor
	*/
	public GetProposalRequest() {
		super();
	}

	/**
	* Default GetProposalRequest constructor
	*/
	public GetProposalRequest(int proposalId) {
		super();
		this.proposalId = proposalId;
	}

	/**
	* Returns value of proposalId
	* @return
	*/
	public int getProposalId() {
		return proposalId;
	}

	/**
	* Sets new value of proposalId
	* @param
	*/
	public void setProposalId(int proposalId) {
		this.proposalId = proposalId;
	}
}
