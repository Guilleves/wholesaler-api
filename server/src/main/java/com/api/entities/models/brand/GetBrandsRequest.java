package com.api.entities.models.brand;
import com.api.entities.models.BaseSearchRequest;

public class GetBrandsRequest extends BaseSearchRequest {
    static final long serialVersionUID = 0L;
    private int id;
    private String name;

	public GetBrandsRequest() {
		super();
	}

	public GetBrandsRequest(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
