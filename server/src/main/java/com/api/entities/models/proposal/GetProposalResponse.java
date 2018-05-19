package com.api.entities.models.proposal;

import com.api.entities.models.organization.GetOrganizationResponse;
import com.api.entities.models.product.GetProductResponse;
import java.util.ArrayList;
import java.util.Date;

public class GetProposalResponse {
    private int id;
    private String title, description, status;
    private Date beginDate, endDate;
    private ArrayList<Line> proposalLines;
    private GetOrganizationResponse supplier;

	/**
	* Default empty GetProposalResponse constructor
	*/
	public GetProposalResponse() {
		super();
	}

	/**
	* Default GetProposalResponse constructor
	*/
	public GetProposalResponse(int id, String title, String description, String status, Date beginDate, Date endDate, ArrayList<Line> proposalLines, GetOrganizationResponse supplier) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.beginDate = beginDate;
		this.endDate = endDate;
        this.proposalLines = proposalLines;
        this.supplier = supplier;
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
	* Returns value of title
	* @return
	*/
	public String getTitle() {
		return title;
	}

	/**
	* Sets new value of title
	* @param
	*/
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	* Returns value of description
	* @return
	*/
	public String getDescription() {
		return description;
	}

	/**
	* Sets new value of description
	* @param
	*/
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	* Returns value of status
	* @return
	*/
	public String getStatus() {
		return status;
	}

	/**
	* Sets new value of status
	* @param
	*/
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	* Returns value of beginDate
	* @return
	*/
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	* Sets new value of beginDate
	* @param
	*/
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	* Returns value of endDate
	* @return
	*/
	public Date getEndDate() {
		return endDate;
	}

	/**
	* Sets new value of endDate
	* @param
	*/
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	* Sets new value of proposalLines
	* @param
	*/
	public void setProposalLines(ArrayList<Line> proposalLines) {
		this.proposalLines = proposalLines;
	}

    /**
	* Returns value of proposalLines
	* @return
	*/
	public ArrayList<Line> getProposalLines() {
		return proposalLines;
	}

    /**
	* Returns value of endDate
	* @return
	*/
	public GetOrganizationResponse getSupplier() {
		return supplier;
	}

	/**
	* Sets new value of endDate
	* @param
	*/
	public void setSupplier(GetOrganizationResponse supplier) {
		this.supplier = supplier;
	}

    public static class Line {
        private int id;
        private GetProductResponse product;
        private float price;

        public Line() {
            super();
        }

        public Line(int id, GetProductResponse product, float price) {
            super();

            this.id = id;
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
        * Returns value of id
        * @return
        */
        public GetProductResponse getProduct() {
            return product;
        }

        /**
        * Sets new value of id
        * @param
        */
        public void setProduct(GetProductResponse product) {
            this.product = product;
        }

        /**
        * Returns value of id
        * @return
        */
        public float getPrice() {
            return price;
        }

        /**
        * Sets new value of id
        * @param
        */
        public void setPrice(float price) {
            this.price = price;
        }

    }
}
