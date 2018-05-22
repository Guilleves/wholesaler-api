package com.api.entities.models.product;

// #region Imports

import java.io.Serializable;

// #endregion

public class GetProductRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private int productId;

	/**
	* Default empty GetProductRequest constructor
	*/
	public GetProductRequest() {
		super();
	}

	/**
	* Default GetProductRequest constructor
	*/
	public GetProductRequest(int productId) {
		super();
		this.productId = productId;
	}

	/**
	* Returns value of productId
	* @return
	*/
	public int getProductId() {
		return productId;
	}

	/**
	* Sets new value of productId
	* @param
	*/
	public void setProductId(int productId) {
		this.productId = productId;
	}
}
