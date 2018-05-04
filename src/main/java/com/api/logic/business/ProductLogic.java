package com.api.logic.business;

// #region Import
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

    public SaveProductResponse saveProduct(SaveProductRequest request) throws ServerResponse {
        SaveProductResponse response = new SaveProductResponse();

        Product product = new Product(
            request.getId(),
            request.getName(),
            request.getGtin(),
            new Brand(request.getBrand().getId(), request.getBrand().getName()),
            new Category(request.getCategory().getId(), request.getCategory().getName())
        );

        ServerResponse sr = validateSaveProduct(product);

        if (!sr.getStatus())
            throw(sr);

        if (product.getId() == 0)
            pda.createProduct(product);
        else
            pda.updateProduct(product);

        return response;
    }

    // #endregion

    // #region Validation

    private ServerResponse validateSaveProduct(Product product) {
        ServerResponse sr = new ServerResponse();

        if (product.getName() == null || product.getName().isEmpty())
            sr.addError("Product name cannot be empty.");

        return sr;
    }

    // #endregion
}
