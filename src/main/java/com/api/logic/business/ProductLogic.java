package com.api.logic.business;

// #region Imports

import com.api.data.business.UserDataAccess;
import com.api.rest.security.UserPrincipal;
import com.api.entities.models.product.SaveProductRequest;
import com.api.entities.models.product.SaveProductResponse;

import java.util.ArrayList;

import com.api.entities.business.Product;
import com.api.entities.business.Brand;
import com.api.entities.business.Category;

import com.api.logic.validations.ServerResponse;

import com.api.entities.models.product.GetProductRequest;
import com.api.entities.models.product.GetProductResponse;

import com.api.data.business.ProductDataAccess;

// #endregion

public class ProductLogic {
    private ProductDataAccess pda;

    // #region Constructors
    public ProductLogic() {
        pda = new ProductDataAccess();
    }

    // #endregion

    // #region ProductSetup

    public GetProductResponse getProduct(GetProductRequest request) throws ServerResponse {
        ServerResponse sr = new ServerResponse();

        // Fetch product.
        Product product = pda.getProduct(request.getProductId());

        if (product == null) {
            sr.addError("Product was not found.");
            throw sr;
        }

        // Generate the response object.
        GetProductResponse response = new GetProductResponse(
            product.getId(),
            product.getName(),
            product.getGtin(),
            product.getBrand().getId(),
            product.getBrand().getName(),
            product.getCategory().getId(),
            product.getCategory().getName()
        );

        return response;
    }

    public ArrayList<GetProductResponse> getProducts() throws ServerResponse {
        ServerResponse sr = new ServerResponse();
        ArrayList<GetProductResponse> response = new ArrayList<GetProductResponse>();

        // Fetch product list.
        ArrayList<Product> products = pda.getProducts();

        if (products == null || products.isEmpty()) {
            sr.addError("Couldn't find products.");
            throw sr;
        }

        // Generate the response object.
        for (Product product : products) {
            response.add(new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getGtin(),
                product.getBrand().getId(),
                product.getBrand().getName(),
                product.getCategory().getId(),
                product.getCategory().getName()
            ));
        }

        return response;
    }

    public SaveProductResponse saveProduct(SaveProductRequest request, UserPrincipal loggedUser) throws ServerResponse {
        SaveProductResponse response = new SaveProductResponse();
        ServerResponse sr = new ServerResponse();

        if(!(loggedUser.getRole().equals("supplier"))) {
            sr.addError("You don't have permissions to access here...");
            throw sr;
        }

        // Search brand.
        Brand brand = pda.getBrand(request.getBrandId());

        // Search category.
        Category category = pda.getCategory(request.getCategoryId());

        // Validate fields.
        sr = validateSaveProduct(request, brand, category);

        if (!sr.getStatus())
            throw(sr);

        Product product = new Product(
            request.getId(),
            request.getName(),
            request.getGtin(),
            brand,
            category
        );

        if (product.getId() == 0)
            pda.createProduct(product);
        else
            pda.updateProduct(product);

        return response;
    }

    // #endregion

    // #region Validation

    private ServerResponse validateSaveProduct(SaveProductRequest product, Brand brand, Category category) {
        ServerResponse sr = new ServerResponse();

        if (pda.validateGtin(product.getGtin()))
            sr.addError("A product with this gtin has already been created.");

        if (product.getName() == null || product.getName().isEmpty())
            sr.addError("Product name cannot be empty.");

        if (product.getGtin() == null || product.getGtin().isEmpty())
            sr.addError("Gtin code cannot be empty.");

        if (brand == null)
            sr.addError("Brand could not be found.");

        if (category == null)
            sr.addError("Category could not be found.");

        return sr;
    }

    // #endregion
}
