package com.api.entities.models.proposal;

public class GetRankingResponse {
    private GetProposalsResponse proposal;
    private float sum;

	/**
	* Default empty GetRankingResponse constructor
	*/
	public GetRankingResponse() {
		super();
	}

	/**
	* Default GetRankingResponse constructor
	*/
	public GetRankingResponse(GetProposalsResponse proposal, float sum) {
		super();
		this.proposal = proposal;
		this.sum = sum;
	}

	/**
	* Returns value of proposal
	* @return
	*/
	public GetProposalsResponse getProposal() {
		return proposal;
	}

	/**
	* Sets new value of proposal
	* @param
	*/
	public void setProposal(GetProposalsResponse proposal) {
		this.proposal = proposal;
	}

	/**
	* Returns value of sum
	* @return
	*/
	public float getSum() {
		return sum;
	}

	/**
	* Sets new value of sum
	* @param
	*/
	public void setSum(float sum) {
		this.sum = sum;
	}
}
