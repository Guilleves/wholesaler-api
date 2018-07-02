package com.api.entities.models.category;
import java.io.Serializable;

public class GetCategoriesRequest implements Serializable {
    static final long serialVersionUID = 0L;
    private int id;
    private String name;

	public GetCategoriesRequest() {
		super();
	}

	public GetCategoriesRequest(int id, String name) {
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
