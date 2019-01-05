package com.api.entities.models.product;

public class DeleteProductResponse {
    private boolean couldDelete;

	/**
	* Default empty DeleteProposalResponse constructor
	*/
	public DeleteProductResponse() {
		super();
	}

	/**
	* Default DeleteProposalResponse constructor
	*/
	public DeleteProductResponse(boolean couldDelete) {
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
