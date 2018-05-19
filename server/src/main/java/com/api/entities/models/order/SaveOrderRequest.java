package com.api.entities.models.order;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class SaveOrderRequest implements Serializable {
    static final long serialVersionUID = 0L;

    private Date dateOrdered;
    private ArrayList<Line> lines;

    /**
	* Default empty SaveOrderRequest constructor
	*/
	public SaveOrderRequest() {
		super();
	}

	/**
	* Default SaveOrderRequest constructor
	*/
	public SaveOrderRequest(Date dateOrdered, ArrayList<Line> lines) {
		super();
        this.dateOrdered = dateOrdered;
		this.lines = lines;
	}


    // #region Getters and setters

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

        private int proposalLineId, quantity;

        public Line() {
            super();
        }

        public Line(int proposalLineId, int quantity) {
            super();

            this.proposalLineId = proposalLineId;
            this.quantity = quantity;
        }

        /**
    	* Returns value of proposalLineId
    	* @return
    	*/
    	public int getProposalLineId() {
    		return proposalLineId;
    	}

    	/**
    	* Sets new value of productId
    	* @param
    	*/
    	public void setProposalLineId(int proposalLineId) {
    		this.proposalLineId = proposalLineId;
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
    }
}
