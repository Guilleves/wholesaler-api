package com.api.rest;

// #region Import

import com.api.rest.util.DateParameter;
import com.api.entities.models.order.GetOrdersRequest;
import javax.ws.rs.QueryParam;
import com.api.entities.business.User;
import com.api.rest.security.UserPrincipal;
import com.api.entities.models.order.SaveOrderRequest;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Context;
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
    public Response getOrders(@QueryParam("retailId") Integer retailId, @QueryParam("fromDate") DateParameter fromDate, @QueryParam("toDate") DateParameter toDate, @QueryParam("orderBy") String orderBy, @QueryParam("pageSize") Integer pageSize, @QueryParam("pageIndex") Integer pageIndex) {
        GetOrdersRequest request = new GetOrdersRequest(
            orderBy,
            pageIndex,
            pageSize,
            retailId,
            fromDate == null ? null : fromDate.getDate(),
            toDate == null ? null : toDate.getDate()
        );

        try {
            return Response.ok(ol.getOrders(request)).build();
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response createOrder(@Context SecurityContext context, SaveOrderRequest request) {
        try {
            User user = ((UserPrincipal)context.getUserPrincipal()).getUser();
            return Response.ok(ol.createOrder(request, user)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    // #endregion
}
