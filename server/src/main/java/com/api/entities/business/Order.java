package com.api.entities.business;

import java.util.ArrayList;
import java.util.Date;

public class Order implements BaseEntity {
    private int id;
    private Date dateOrdered;
    private ArrayList<OrderLine> orderLines;
    private Retail retail;

	/**
	* Default empty Order constructor
	*/
	public Order() {
		super();
        orderLines = new ArrayList<OrderLine>();
	}

	/**
	* Default Order constructor
	*/
	public Order(int id, Date dateOrdered, Retail retail) {
		super();
        orderLines = new ArrayList<OrderLine>();

		this.id = id;
		this.dateOrdered = dateOrdered;
        this.retail = retail;
	}

	/**
	* Returns value of id
	* @return
	*/
	public int getId() {
		return id;
	}

	/**
	* Sets new value of id
	* @param
	*/
	public void setId(int id) {
		this.id = id;
	}

	/**
	* Returns value of dateOrdered
	* @return
	*/
	public Date getDateOrdered() {
		return dateOrdered;
	}

	/**
	* Sets new value of dateOrdered
	* @param
	*/
	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	/**
	* Returns value of orderLines
	* @return
	*/
	public ArrayList<OrderLine> getOrderLines() {
		return orderLines;
	}

	/**
	* Sets new value of orderLines
	* @param
	*/
	public void setOrderLines(ArrayList<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	/**
	* Returns value of retail
	* @return
	*/
	public Retail getRetail() {
		return retail;
	}

	/**
	* Sets new value of retail
	* @param
	*/
	public void setRetail(Retail retail) {
		this.retail = retail;
	}
}
