package com.api.logic.business;

// #region Imports

import java.util.ArrayList;

import com.api.entities.business.Product;

import com.api.logic.validations.ServerResponse;

import com.api.entities.models.product.GetProductRequest;
import com.api.entities.models.product.GetProductResponse;

import com.api.data.business.ProductDataAccess;

// #endregion

public class ProductLogic {
    private ProductDataAccess pda;

    public ProductLogic() {
        pda = new ProductDataAccess();
    }

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
            product.getBrand(),
            product.getCategory()
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
                product.getBrand(),
                product.getCategory()
            ));
        }

        return response;
    }
}
