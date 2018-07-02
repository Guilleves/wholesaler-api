package com.api.entities.models.proposal;

public class DeleteProposalResponse {
    private boolean couldDelete;

	/**
	* Default empty DeleteProposalResponse constructor
	*/
	public DeleteProposalResponse() {
		super();
	}

	/**
	* Default DeleteProposalResponse constructor
	*/
	public DeleteProposalResponse(boolean couldDelete) {
		super();
		this.couldDelete = couldDelete;
	}

	/**
	* Returns value of couldDelete
	* @return
	*/
	public boolean isCouldDelete() {
		return couldDelete;
	}

	/**
	* Sets new value of couldDelete
	* @param
	*/
	public void setCouldDelete(boolean couldDelete) {
		this.couldDelete = couldDelete;
	}
}
