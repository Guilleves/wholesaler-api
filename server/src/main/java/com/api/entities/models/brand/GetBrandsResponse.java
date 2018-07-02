package com.api.entities.models.brand;

import com.api.entities.models.BaseSearchResponse;

public class GetBrandsResponse implements BaseSearchResponse.SearchItem {
    private int id;
    private String name;

	public GetBrandsResponse() {
		super();
	}

	public GetBrandsResponse(int id, String name) {
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
