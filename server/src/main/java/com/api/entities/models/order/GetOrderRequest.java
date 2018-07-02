package com.api.entities.models.order;

import java.io.Serializable;

public class GetOrderRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private int orderId;

	/**
	* Default empty GetOrderRequest constructor
	*/
	public GetOrderRequest() {
		super();
	}

	/**
	* Default GetOrderRequest constructor
	*/
	public GetOrderRequest(int orderId) {
		super();
		this.orderId = orderId;
	}

	/**
	* Returns value of orderId
	* @return
	*/
	public int getOrderId() {
		return orderId;
	}

	/**
	* Sets new value of orderId
	* @param
	*/
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
}
