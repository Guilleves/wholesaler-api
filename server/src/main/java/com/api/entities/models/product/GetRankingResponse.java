package com.api.entities.models.product;

public class GetRankingResponse {
    private int id, count;
    private String name, gtin, description;
    private Brand brand;
    private Category category;

	/**
	* Default empty GetProductResponse constructor
	*/
	public GetRankingResponse() {
		super();
	}

	/**
	* Default GetProductResponse constructor
	*/
	public GetRankingResponse(int id, int count, String name, String gtin, String description, int brandId, String brandName, int categoryId, String categoryName) {
		super();
		this.id = id;
    this.count = count;
		this.name = name;
    this.gtin = gtin;
		this.description = description;
		this.brand = new Brand(brandId, brandName);
		this.category = new Category(categoryId, categoryName);
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
    public int getCount() {
        return count;
    }

    /**
    * Sets new value of id
    * @param
    */
    public void setCount(int count) {
        this.count = count;
    }

	/**
	* Returns value of name
	* @return
	*/
	public String getName() {
		return name;
	}

	/**
	* Sets new value of name
	* @param
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	* Returns value of gtin
	* @return
	*/
	public String getGtin() {
		return gtin;
	}

	/**
	* Sets new value of gtin
	* @param
	*/
	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

  public String getDescription(){
    return description;
  }

  public void setDescription(String description){
    this.description = description;
  }
	/**
	* Returns value of brand
	* @return
	*/
	public Brand getBrand() {
		return brand;
	}

	/**
	* Sets new value of brand
	* @param
	*/
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	/**
	* Returns value of category
	* @return
	*/
	public Category getCategory() {
		return category;
	}

	/**
	* Sets new value of category
	* @param
	*/
	public void setCategory(Category category) {
		this.category = category;
	}

    class Brand {
        private int id;
        private String name;

        public Brand(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Category {
        private int id;
        private String name;

        public Category(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
