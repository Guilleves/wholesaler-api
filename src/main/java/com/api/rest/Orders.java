package com.api.rest;

// #region Imports

import com.api.entities.models.order.GetOrderRequest;
import com.api.logic.business.OrderLogic;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.logic.validations.ApiException;

import com.api.rest.security.Secured;

// #endregion

@Path("/orders")
public class Orders {
    private OrderLogic ol;

    // #region Constructors

    public Orders() {
        ol = new OrderLogic();
    }

    // #endregion

    // #region ProposalSetup

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response getOrders() {
        try {
            return Response.ok(ol.getOrders()).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/{orderId}")
    public Response getOrder(@PathParam("orderId") int orderId) {
        GetOrderRequest request = new GetOrderRequest(orderId);

        try {
            return Response.ok(ol.getOrder(request)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    // #endregion
}
