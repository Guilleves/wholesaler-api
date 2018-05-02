package com.api.entities.business;

public class ProposalLine {
    private int id;
    private Proposal proposal;
    private Product product;
    private float price;

	/**
	* Default empty ProposalLine constructor
	*/
	public ProposalLine() {
		super();
	}

	/**
	* Default ProposalLine constructor
	*/
	public ProposalLine(int id, Proposal proposal, Product product, float price) {
		super();
		this.id = id;
		this.proposal = proposal;
		this.product = product;
		this.price = price;
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
	* Returns value of proposal
	* @return
	*/
	public Proposal getProposal() {
		return proposal;
	}

	/**
	* Sets new value of proposal
	* @param
	*/
	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	/**
	* Returns value of product
	* @return
	*/
	public Product getProduct() {
		return product;
	}

	/**
	* Sets new value of product
	* @param
	*/
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	* Returns value of price
	* @return
	*/
	public float getPrice() {
		return price;
	}

	/**
	* Sets new value of price
	* @param
	*/
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	* Create string representation of ProposalLine for printing
	* @return
	*/
	@Override
	public String toString() {
		return "ProposalLine [id=" + id + ", product=" + product.toString() + ", price=" + price + "]";
	}
}
