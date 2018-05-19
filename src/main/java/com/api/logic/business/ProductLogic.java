package com.api.logic.business;

// #region Imports

import com.api.entities.business.Ranking;
import com.api.entities.models.product.GetRankingResponse;
import java.sql.SQLException;
import com.api.entities.models.product.GetProductsRequest;
import com.api.entities.business.User;
import com.api.entities.enums.OrganizationRoles;
import javax.ws.rs.core.Response.Status;

import com.api.entities.models.product.SaveProductRequest;

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
        try {
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
        catch(SQLException ex) {
            throw new ApiException(ex);
        }
    }

    public ArrayList<GetProductResponse> getProducts(GetProductsRequest request) throws ApiException {
        try {
            ArrayList<GetProductResponse> response = new ArrayList<GetProductResponse>();

            // Fetch product list.
            ArrayList<Product> products = pda.getProducts(
                request.getBrandId(),
                request.getCategoryId(),
                request.getPageIndex(),
                request.getPageSize(),
                request.getKeyword(),
                request.getOrderBy()
            );

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
        catch(SQLException ex) {
            throw new ApiException(ex);
        }
    }

    public ArrayList<GetRankingResponse> mostUsedByProposal() throws ApiException {
        try {
            ArrayList<Ranking> products = pda.mostUsedByProposal();

            if (products == null || products.isEmpty())
                throw new ApiException("Product was not found.", Status.NOT_FOUND);

            ArrayList<GetRankingResponse> response = new ArrayList<GetRankingResponse>();

            // Generate the response object.
            for (Ranking product : products) {
                response.add(new GetRankingResponse(
                    ((Product)product.getEntity()).getId(),
                    product.getCount(),
                    ((Product)product.getEntity()).getName(),
                    ((Product)product.getEntity()).getGtin(),
                    ((Product)product.getEntity()).getBrand().getId(),
                    ((Product)product.getEntity()).getBrand().getName(),
                    ((Product)product.getEntity()).getCategory().getId(),
                    ((Product)product.getEntity()).getCategory().getName()
                ));
            }

            return response;
        }
        catch(SQLException e) {
            throw new ApiException(e);
        }
    }

    public GetProductResponse createProduct(SaveProductRequest request, User loggedUser) throws ApiException {
        try {
            if(!(loggedUser.getOrganization().getRole().equals(OrganizationRoles.SUPPLIER)))
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

            product = pda.createProduct(product);

            return new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getGtin(),
                product.getBrand().getId(),
                product.getBrand().getName(),
                product.getCategory().getId(),
                product.getCategory().getName()
            );
        }
        catch (SQLException ex) {
            throw new ApiException(ex);
        }
    }

    public GetProductResponse updateProduct(SaveProductRequest request, User loggedUser) throws ApiException {
        try {
            if(!(loggedUser.getOrganization().getRole().equals(OrganizationRoles.SUPPLIER)))
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

            int amtOfRows = pda.updateProduct(product);

            GetProductResponse response = new GetProductResponse();

            if (amtOfRows != 0)
                response = new GetProductResponse(
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
        catch (SQLException ex) {
            throw new ApiException(ex);
        }
    }

    // #endregion

    // #region Validation

    private ApiException validateSaveProduct(SaveProductRequest product, Brand brand, Category category) throws SQLException {
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
