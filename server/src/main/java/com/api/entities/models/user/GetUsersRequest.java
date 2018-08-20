package com.api.entities.models.user;

import com.api.entities.models.BaseSearchRequest;

public class GetUsersRequest extends BaseSearchRequest {
  static final long serialVersionUID = 0L;
  private String keyword;

	/**
	* Default empty GetUsersRequest constructor
	*/
	public GetUsersRequest() {
		super();
	}

	/**
	* Default GetUsersRequest constructor
	*/
	public GetUsersRequest(String keyword, String orderBy, Integer pageIndex, Integer pageSize) {
    super(orderBy, pageIndex, pageSize);
		this.keyword = keyword;
	}

	/**
	* Returns value of keyword
	* @return
	*/
	public String getKeyword() {
		return keyword;
	}

	/**
	* Sets new value of keyword
	* @param
	*/
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
