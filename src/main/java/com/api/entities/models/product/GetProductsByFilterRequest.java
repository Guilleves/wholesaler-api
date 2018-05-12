package com.api.entities.models.product;
import java.io.Serializable;

public class GetProductsByFilterRequest implements Serializable {
    static final long serialVersionUID = 0L;
    private int brandId, categoryId;
    private String keyword;

	public GetProductsByFilterRequest() {
		super();
	}

	public GetProductsByFilterRequest(int brandId, int categoryId, String keyword) {
		super();
		this.brandId = brandId;
		this.categoryId = categoryId;
		this.keyword = keyword;
	}

    public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getKeyword() {
		return keyword;
	}
 
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
