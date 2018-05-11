package com.api.entities.models.product;

public class GetProductsByBrandResponse {
    private int id;
    private String name, gtin;
    private Brand brand;
    private Category category;

	public GetProductsByBrandResponse() {
		super();
	}

	public GetProductsByBrandResponse(int id, String name, String gtin, int brandId, String brandName, int categoryId, String categoryName) {
		super();
		this.id = id;
		this.name = name;
		this.gtin = gtin;
		this.brand = new Brand(brandId, brandName);
		this.category = new Category(categoryId, categoryName);
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

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return category;
	}

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
