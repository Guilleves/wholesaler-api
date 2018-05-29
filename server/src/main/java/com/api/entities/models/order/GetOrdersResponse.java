package com.api.entities.models.order;

import com.api.entities.models.organization.GetOrganizationResponse;
import java.util.Date;
import com.api.entities.models.BaseSearchResponse;

public class GetOrdersResponse implements BaseSearchResponse.SearchItem {
    private int id;
    private Date dateOrdered;
    private GetOrganizationResponse retail;

	/**
	* Default empty GetOrdersResponse constructor
	*/
	public GetOrdersResponse() {
		super();
	}

	/**
	* Default GetOrdersResponse constructor
	*/
	public GetOrdersResponse(int id, Date dateOrdered, GetOrganizationResponse retail) {
		super();
		this.id = id;
		this.dateOrdered = dateOrdered;
		this.retail = retail;
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
	* Returns value of dateOrdered
	* @return
	*/
	public Date getDateOrdered() {
		return dateOrdered;
	}

	/**
	* Sets new value of dateOrdered
	* @param
	*/
	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	/**
	* Returns value of retail
	* @return
	*/
	public GetOrganizationResponse getRetail() {
		return retail;
	}

	/**
	* Sets new value of retail
	* @param
	*/
	public void setRetail(GetOrganizationResponse retail) {
		this.retail = retail;
	}
}
