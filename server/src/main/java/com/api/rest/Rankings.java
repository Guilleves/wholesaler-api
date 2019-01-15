package com.api.rest;

// #region Imports

import com.api.logic.business.OrganizationLogic;
import com.api.entities.business.User;
import com.api.rest.security.UserPrincipal;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.DELETE;
import com.api.logic.business.ProductLogic;
import com.api.logic.business.ProposalLogic;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.logic.validations.ApiException;

import com.api.rest.security.Secured;

// #endregion

@Path("/rankings")
public class Rankings {
  private ProductLogic pl;
  private OrganizationLogic orl;

  // #region Constructors

  public Rankings() {
    pl = new ProductLogic();
    orl = new OrganizationLogic();
  }

  // #endregion

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Secured()
  @Path("/products")
  public Response getProduct(@Context SecurityContext context, @QueryParam("amount") int amount, @QueryParam("orderBy") String orderBy) {
    try {
      User user = ((UserPrincipal)context.getUserPrincipal()).getUser();
      if (orderBy.equals("proposal"))
      return Response.ok(pl.mostUsedByProposal(user, amount)).build();
      else
      return Response.status(Status.BAD_REQUEST).build();
    }
    catch(ApiException e) {
      return Response.status(e.getStatus()).entity(e.getErrors()).build();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Secured()
  @Path("/organizations")
  public Response getOrganizations(@Context SecurityContext context, @QueryParam("amount") int amount, @QueryParam("type") String type) {
    try {
      User user = ((UserPrincipal)context.getUserPrincipal()).getUser();

      if (type.equals("profit"))
      return Response.ok(orl.getProfitsByOrganization(user, amount)).build();
      else
      return Response.status(Status.BAD_REQUEST).build();
    }
    catch(ApiException e) {
      return Response.status(e.getStatus()).entity(e.getErrors()).build();
    }
  }
}
