package com.api.rest;

// #region Imports

import com.api.entities.models.category.GetCategoriesRequest;
import com.api.logic.business.CategoryLogic;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.logic.validations.ApiException;

import com.api.rest.security.Secured;

// #endregion

@Path("/categories")
public class Categories {
    private CategoryLogic cl;

    // #region Constructors

    public Categories() {
        cl = new CategoryLogic();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response getCategories() {
        GetCategoriesRequest request = new GetCategoriesRequest();

        try {
            return Response.ok(cl.getCategories(request)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }
}
