package com.api.rest;

// #region Imports
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

import com.api.logic.validations.ServerResponse;

import com.api.rest.security.Secured;

    // #endregion

@Path("/users")
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
    public Response getProducts() {
        try {
            return Response.ok(pl.getProducts()).build();
        }
        catch(ServerResponse e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getErrores()).build();
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
        catch(ServerResponse e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getErrores()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response saveProduct(SaveProductRequest request) {
        try {
            pl.saveProduct(request);
            return Response.ok().build();
        }
        catch(ServerResponse e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getErrores()).build();
        }
    }

    // #endregion
}
