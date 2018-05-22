package com.api.logic.business;

// #region Imports

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

    public ArrayList<GetBrandsResponse> getBrands(GetBrandsRequest request) throws ApiException {
        try {
            ArrayList<GetBrandsResponse> response = new ArrayList<GetBrandsResponse>();

            // Fetch product list.
            ArrayList<Brand> brands = bda.getBrands();
            if (brands == null || brands.isEmpty())
                throw new ApiException("Couldn't find any brands.", Status.NOT_FOUND);

            // Generate the response object.
            for (Brand brand : brands) {
                response.add(new GetBrandsResponse(
                    brand.getId(),
                    brand.getName()
                ));
            }

            return response;
        }
        catch(SQLException ex) {
            throw new ApiException(ex);
        }
    }
}
