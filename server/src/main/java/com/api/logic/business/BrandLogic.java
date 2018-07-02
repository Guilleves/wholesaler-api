package com.api.logic.business;

// #region Imports

import com.api.entities.models.BaseSearchResponse;
import com.api.entities.models.brand.GetBrandsRequest;
import com.api.entities.models.brand.GetBrandsResponse;
import com.api.data.business.BrandDataAccess;
import com.api.entities.business.Brand;
import java.sql.SQLException;
import javax.ws.rs.core.Response.Status;

import java.util.ArrayList;

import com.api.logic.validations.ApiException;


public class BrandLogic {
    private BrandDataAccess bda;

    public BrandLogic() {
        bda = new BrandDataAccess();
    }

    public GetBrandsResponse getBrand(int brandId) throws ApiException {
        try {
            Brand brand = bda.getBrand(brandId);

            if (brand == null)
                throw new ApiException("Brand not found", Status.NOT_FOUND);

            return new GetBrandsResponse(brand.getId(), brand.getName());
        }
        catch(SQLException ex) {
            throw new ApiException(ex);
        }
    }

    public BaseSearchResponse getBrands(GetBrandsRequest request) throws ApiException {
        try {
            ArrayList<GetBrandsResponse> response = new ArrayList<GetBrandsResponse>();

            // Fetch product list.
            ArrayList<Brand> brands;

            if (request.getPageSize() == null || request.getPageIndex() == null)
                brands = bda.getBrands();
            else
                brands = bda.getBrands(request.getPageSize(), request.getPageIndex());

            if (brands == null || brands.isEmpty())
                throw new ApiException("Couldn't find any brands.", Status.NOT_FOUND);

            // Generate the response object.
            for (Brand brand : brands) {
                response.add(new GetBrandsResponse(
                    brand.getId(),
                    brand.getName()
                ));
            }

            return new BaseSearchResponse(bda.countBrands(), response);
        }
        catch(SQLException ex) {
            throw new ApiException(ex);
        }
    }
}
