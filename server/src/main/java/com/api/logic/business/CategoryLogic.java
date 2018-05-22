package com.api.logic.business;

// #region Imports

import com.api.entities.models.category.GetCategoriesRequest;
import com.api.entities.models.category.GetCategoriesResponse;
import com.api.data.business.CategoryDataAccess;
import com.api.entities.business.Category;
import java.sql.SQLException;
import javax.ws.rs.core.Response.Status;

import java.util.ArrayList;

import com.api.logic.validations.ApiException;


public class CategoryLogic {
    private CategoryDataAccess bda;

    public CategoryLogic() {
        bda = new CategoryDataAccess();
    }

    public ArrayList<GetCategoriesResponse> getCategories(GetCategoriesRequest request) throws ApiException {
        try {
            ArrayList<GetCategoriesResponse> response = new ArrayList<GetCategoriesResponse>();

            // Fetch product list.
            ArrayList<Category> categories = bda.getCategories();

            if (categories == null || categories.isEmpty())
                throw new ApiException("Couldn't find any categories.", Status.NOT_FOUND);

            // Generate the response object.
            for (Category category : categories) {
                response.add(new GetCategoriesResponse(
                    category.getId(),
                    category.getName()
                ));
            }

            return response;
        }
        catch(SQLException ex) {
            throw new ApiException(ex);
        }
    }
}
