package com.api.entities.business;

public class Ranking {
    private int count;
    private BaseEntity entity;

	/**
	* Default empty Ranking constructor
	*/
	public Ranking() {
		super();
	}

	/**
	* Default Ranking constructor
	*/
	public Ranking(int count, BaseEntity entity) {
		super();
		this.count = count;
		this.entity = entity;
	}

	/**
	* Returns value of count
	* @return
	*/
	public int getCount() {
		return count;
	}

	/**
	* Sets new value of count
	* @param
	*/
	public void setCount(int count) {
		this.count = count;
	}

	/**
	* Returns value of entity
	* @return
	*/
	public BaseEntity getEntity() {
		return entity;
	}

	/**
	* Sets new value of entity
	* @param
	*/
	public void setEntity(BaseEntity entity) {
		this.entity = entity;
	}
}
