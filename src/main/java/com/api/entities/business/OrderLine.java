package com.api.entities.business;

public class OrderLine {
    private int id, quantity;
    private Order order;
    private ProposalLine proposalLine;

	/**
	* Default empty OrderLine constructor
	*/
	public OrderLine() {
		super();
	}

	/**
	* Default OrderLine constructor
	*/
	public OrderLine(int id, int quantity, Order order, ProposalLine proposalLine) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.order = order;
		this.proposalLine = proposalLine;
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
	* Returns value of quantity
	* @return
	*/
	public int getQuantity() {
		return quantity;
	}

	/**
	* Sets new value of quantity
	* @param
	*/
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	* Returns value of order
	* @return
	*/
	public Order getOrder() {
		return order;
	}

	/**
	* Sets new value of order
	* @param
	*/
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	* Returns value of proposalLine
	* @return
	*/
	public ProposalLine getProposalLine() {
		return proposalLine;
	}

	/**
	* Sets new value of proposalLine
	* @param
	*/
	public void setProposalLine(ProposalLine proposalLine) {
		this.proposalLine = proposalLine;
	}
}
