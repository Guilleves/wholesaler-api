package com.api.entities.business;

public class Ranking implements BaseEntity {
    private int count;
    private BaseEntity entity;
    private float sum;
    private String date;

	/**
	* Default empty Ranking constructor
	*/
	public Ranking() {
		super();
	}

    /**
	* Default Ranking constructor
	*/
	public Ranking(float sum, BaseEntity entity) {
		super();
		this.entity = entity;
		this.sum = sum;
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
    * Default Ranking constructor
    */
    public Ranking(int count, String date) {
        super();
        this.count = count;
        this.date = date;
    }

	/**
	* Default Ranking constructor
	*/
	public Ranking(int count, BaseEntity entity, float sum) {
		super();
		this.count = count;
		this.entity = entity;
		this.sum = sum;
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

	/**
	* Returns value of sum
	* @return
	*/
	public float getSum() {
		return sum;
	}

	/**
	* Sets new value of sum
	* @param
	*/
	public void setSum(float sum) {
		this.sum = sum;
	}

  public void setDate(String date) {
    this.date = date;
  }

  public String getDate() {
    return date;
  }
}
