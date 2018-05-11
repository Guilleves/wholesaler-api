package com.api.entities.models.proposal;

// #region Imports

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

// #endregion

public class SaveProposalRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private String title, description;
    private Date beginDate, endDate;
    private ArrayList<Line> lines;

    /**
	* Default empty SaveProposalRequest constructor
	*/
	public SaveProposalRequest() {
		super();
	}

	/**
	* Default SaveProposalRequest constructor
	*/
	public SaveProposalRequest(String title, String description, Date beginDate, Date endDate, ArrayList<Line> lines) {
		super();
        this.title = title;
		this.description = description;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.lines = lines;
	}


    // #region Getters and setters
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
	* Returns value of lines
	* @return
	*/
	public ArrayList<Line> getLines() {
		return lines;
	}

	/**
	* Sets new value of lines
	* @param
	*/
	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}

    // #endregion

    public static class Line implements Serializable {
        static final long serialVersionUID = 0L;

        private int productId;
        private float price;

        public Line() {
            super();
        }

        public Line(int productId, float price) {
            super();

            this.productId = productId;
            this.price = price;
        }

        /**
    	* Returns value of productId
    	* @return
    	*/
    	public int getProductId() {
    		return productId;
    	}

    	/**
    	* Sets new value of productId
    	* @param
    	*/
    	public void setProductId(int productId) {
    		this.productId = productId;
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
    }
}
