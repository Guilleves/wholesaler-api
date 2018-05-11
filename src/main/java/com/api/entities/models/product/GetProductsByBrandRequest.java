package com.api.entities.models.product;
import java.io.Serializable;

public class GetProductsByBrandRequest implements Serializable {
    static final long serialVersionUID = 0L;
    private int brandId;

	public GetProductsByBrandRequest() {
		super();
	}

	public GetProductsByBrandRequest(int brandId) {
		super();
		this.brandId = brandId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
}
