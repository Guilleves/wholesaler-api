package com.api.logic.business;

// #region Imports

import com.api.entities.models.product.GetProductsByBrandRequest;
import com.api.entities.models.product.GetProductsByBrandResponse;
import com.api.entities.enums.OrganizationRoles;
import javax.ws.rs.core.Response.Status;

import com.api.rest.security.UserPrincipal;
import com.api.entities.models.product.SaveProductRequest;
import com.api.entities.models.product.SaveProductResponse;

import java.util.ArrayList;

import com.api.entities.business.Product;
import com.api.entities.business.Brand;
import com.api.entities.business.Category;

import com.api.logic.validations.ApiException;

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

    public GetProductResponse getProduct(GetProductRequest request) throws ApiException {
        // Fetch product.
        Product product = pda.getProduct(request.getProductId());

        if (product == null)
            throw new ApiException("Product was not found.", Status.NOT_FOUND);


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

    public ArrayList<GetProductResponse> getProducts() throws ApiException {
        ArrayList<GetProductResponse> response = new ArrayList<GetProductResponse>();

        // Fetch product list.
        ArrayList<Product> products = pda.getProducts();

        if (products == null || products.isEmpty())
            throw new ApiException("Couldn't find products.", Status.NOT_FOUND);

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

    public ArrayList<GetProductsByBrandResponse> getProductsByBrand(GetProductsByBrandRequest request) throws ApiException {
        ArrayList<GetProductsByBrandResponse> response = new ArrayList<GetProductsByBrandResponse>();

        ArrayList<Product> products = pda.getProducts();

        if (products == null || products.isEmpty())
            throw new ApiException("There are no products with that brand yet.", Status.NOT_FOUND);

        for (Product product : products) {
            response.add(new GetProductsByBrandResponse(
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

    public SaveProductResponse saveProduct(SaveProductRequest request, UserPrincipal loggedUser) throws ApiException {
        SaveProductResponse response = new SaveProductResponse();

        if(!(loggedUser.getRole().equals(OrganizationRoles.SUPPLIER)))
            throw new ApiException("You don't have permissions to access here.", Status.UNAUTHORIZED);

        // Search brand.
        Brand brand = pda.getBrand(request.getBrandId());

        // Search category.
        Category category = pda.getCategory(request.getCategoryId());

        // Validate fields.
        ApiException ex = validateSaveProduct(request, brand, category);

        if (!ex.isOk())
            throw(ex);

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

    private ApiException validateSaveProduct(SaveProductRequest product, Brand brand, Category category) {
        ApiException ex = new ApiException();

        if (pda.validateGtin(product.getGtin()))
            ex.addError("A product with this gtin has already been created.");

        if (product.getName() == null || product.getName().isEmpty())
            ex.addError("Product name cannot be empty.");

        if (product.getGtin() == null || product.getGtin().isEmpty())
            ex.addError("Gtin code cannot be empty.");

        if (brand == null)
            ex.addError("Brand could not be found.");

        if (category == null)
            ex.addError("Category could not be found.");

        return ex;
    }

    // #endregion
}
