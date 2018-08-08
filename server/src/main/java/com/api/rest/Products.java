package com.api.rest;

// #region Imports

import com.api.entities.models.product.GetProductsRequest;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import com.api.entities.business.User;
import com.api.rest.security.UserPrincipal;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.entities.models.product.GetProductRequest;
import com.api.entities.models.product.SaveProductRequest;

import com.api.logic.business.ProductLogic;

import com.api.logic.validations.ApiException;

import com.api.rest.security.Secured;

// #endregion

@Path("/products")
public class Products {
    private ProductLogic pl;

    // #region Constructors

    public Products() {
        pl = new ProductLogic();
    }

    // #endregion

    // #region ProductSetup

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response getProducts(@QueryParam("brandId") Integer brandId, @QueryParam("categoryId") Integer categoryId, @QueryParam("keyword") String keyword, @QueryParam("orderBy") String orderBy, @QueryParam("pageIndex") Integer pageIndex, @QueryParam("pageSize") Integer pageSize) {
        GetProductsRequest request = new GetProductsRequest(
            brandId,
            categoryId,
            pageIndex,
            pageSize,
            keyword,
            orderBy
        );

        try {
            return Response.ok(pl.getProducts(request)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/{productId}")
    public Response getProduct(@PathParam("productId") int productId) {
        GetProductRequest request = new GetProductRequest(productId);

        try {
            return Response.ok(pl.getProduct(request)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/count")
    public Response count() {
        try {
            return Response.ok(pl.countProducts()).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response createProduct(@Context SecurityContext context, SaveProductRequest request) {
        try {
            User loggedUser = ((UserPrincipal)context.getUserPrincipal()).getUser();
            pl.createProduct(request, loggedUser);
            return Response.ok().build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/{productId}")
    public Response updateProduct(@PathParam("productId") int productId, @Context SecurityContext context, SaveProductRequest request) {
        try {
            request.setId(productId);

            User loggedUser = ((UserPrincipal)context.getUserPrincipal()).getUser();
            pl.updateProduct(request, loggedUser);
            return Response.ok().build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }


    // #endregion
}
