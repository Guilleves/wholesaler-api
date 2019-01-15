package com.api.entities.models.organization;

public class GetRankingResponse {
    private GetOrganizationResponse organiztion;
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
	public GetRankingResponse(GetOrganizationResponse organiztion, float sum) {
		super();
		this.organiztion = organiztion;
		this.sum = sum;
	}

	/**
	* Returns value of proposal
	* @return
	*/
	public GetOrganizationResponse getOrganization() {
		return organiztion;
	}

	/**
	* Sets new value of proposal
	* @param
	*/
	public void setOrganization(GetOrganizationResponse organization) {
		this.organiztion = organiztion;
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
