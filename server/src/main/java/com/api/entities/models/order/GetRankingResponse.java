package com.api.entities.models.order;

public class GetRankingResponse {
  private int count;
  private String date;

  /**
  * Default empty GetRankingResponse constructor
  */
  public GetRankingResponse() {
    super();
  }

  /**
  * Default GetRankingResponse constructor
  */
  public GetRankingResponse(String date, int count) {
    super();
    this.date = date;
    this.count = count;
  }

	/**
	* Returns value of count
	* @return
	*/
	public int getCount() {
		return count;
	}

	/**
	* Sets new value of count
	* @param
	*/
	public void setCount(int count) {
		this.count = count;
	}

	/**
	* Returns value of date
	* @return
	*/
	public String getDate() {
		return date;
	}

	/**
	* Sets new value of date
	* @param
	*/
	public void setDate(String date) {
		this.date = date;
	}
}
