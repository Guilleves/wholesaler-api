package com.api.entities.models;

import java.util.ArrayList;

public class BaseSearchResponse {
    private int size;
    private ArrayList<? extends SearchItem> items;

    public static interface SearchItem {
    }

	/**
	* Returns value of size
	* @return
	*/
	public int getSize() {
		return size;
	}

	/**
	* Sets new value of size
	* @param
	*/
	public void setSize(int size) {
		this.size = size;
	}

	/**
	* Returns value of items
	* @return
	*/
	public ArrayList<? extends SearchItem> getItems() {
		return items;
	}

	/**
	* Sets new value of items
	* @param
	*/
	public <T extends SearchItem> void setItems(ArrayList<T> items) {
		this.items = items;
	}

	/**
	* Default empty BaseSearchResponse constructor
	*/
	public BaseSearchResponse() {
		super();
	}

	/**
	* Default BaseSearchResponse constructor
	*/
	public <T extends SearchItem> BaseSearchResponse(int size, ArrayList<T> items) {
		super();
		this.size = size;
		this.items = items;
	}
}
