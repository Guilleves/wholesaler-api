package com.api.rest;

// #region Imports
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.api.entities.models.brand.GetBrandsRequest;
import com.api.logic.business.BrandLogic;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.logic.validations.ApiException;

import com.api.rest.security.Secured;

// #endregion

@Path("/brands")
public class Brands {
    private BrandLogic bl;

    // #region Constructors

    public Brands() {
        bl = new BrandLogic();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/{brandId}")
    public Response getBrands(@PathParam("brandId") int brandId) {
        try {
            return Response.ok(bl.getBrand(brandId)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response getBrands(@QueryParam("pageIndex") Integer pageIndex, @QueryParam("pageSize") Integer pageSize) {
        GetBrandsRequest request = new GetBrandsRequest();

        request.setPageSize(pageSize);
        request.setPageIndex(pageIndex);

        try {
            return Response.ok(bl.getBrands(request)).build();
        }
        catch(ApiException e) {
            System.out.print("Llegué al catch");
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }
}
