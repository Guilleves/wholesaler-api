package com.api.entities.models.order;

import com.api.entities.models.organization.GetOrganizationResponse;
import com.api.entities.models.product.GetProductResponse;
import java.util.ArrayList;
import java.util.Date;

public class GetOrderResponse {
    private int id;
    private Date dateOrdered;
    private ArrayList<OrderLine> orderLines;
    private GetOrganizationResponse retail;

    public GetOrderResponse() {
        super();
    }

    public GetOrderResponse(int id, Date dateOrdered, ArrayList<OrderLine> orderLines, GetOrganizationResponse retail) {
        this.id = id;
        this.dateOrdered = dateOrdered;
        this.orderLines = orderLines;
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
    * Returns value of id
    * @return
    */
    public Date getDateOrdered() {
        return dateOrdered;
    }

    /**
    * Sets new value of id
    * @param
    */
    public void setDateOrdered(Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    /**
    * Returns value of id
    * @return
    */
    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    /**
    * Sets new value of id
    * @param
    */
    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    /**
    * Returns value of id
    * @return
    */
    public GetOrganizationResponse getRetail() {
        return retail;
    }

    /**
    * Sets new value of id
    * @param
    */
    public void setRetail(GetOrganizationResponse retail) {
        this.retail = retail;
    }

    public static class OrderLine {
        private int id, quantity;
        private ProposalLine proposalLine;

        public OrderLine() {
            super();
        }

        public OrderLine(int id, int quantity, ProposalLine proposalLine) {
            super();

            this.id = id;
            this.quantity = quantity;
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
        * Returns value of id
        * @return
        */
        public int getQuantity() {
            return quantity;
        }

        /**
        * Sets new value of id
        * @param
        */
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        /**
        * Returns value of id
        * @return
        */
        public ProposalLine getProposalLine() {
            return proposalLine;
        }

        /**
        * Sets new value of id
        * @param
        */
        public void setProposalLine(ProposalLine proposalLine) {
            this.proposalLine = proposalLine;
        }

        public static class ProposalLine {
            private int id;
            private Proposal proposal;
            private GetProductResponse product;
            private float price;

            public ProposalLine() {
                super();
            }

            public ProposalLine(int id, Proposal proposal, GetProductResponse product, float price) {
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
            * Sets new value of id
            * @param
            */
            public void setProposal(Proposal proposal) {
                this.proposal = proposal;
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

            public static class Proposal {
                private int id;
                private Date beginDate, endDate;
                private String title, description;

                public Proposal() {
                    super();
                }

                public Proposal(int id, Date beginDate, Date endDate, String title, String description) {
                    super();
                    this.id = id;
                    this.beginDate = beginDate;
                    this.endDate = endDate;
                    this.title = title;
                    this.description = description;
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
            }
        }
    }
}
