package com.api.rest;

// #region Imports

import javax.ws.rs.core.Response.Status;
import com.api.logic.business.ProductLogic;
import com.api.entities.models.proposal.GetProposalsRequest;
import javax.ws.rs.QueryParam;
import com.api.entities.business.User;
import com.api.rest.security.UserPrincipal;
import com.api.entities.models.proposal.SaveProposalRequest;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.logic.business.ProposalLogic;

import com.api.logic.validations.ApiException;

import com.api.rest.security.Secured;

// #endregion

@Path("/rankings")
public class Rankings {
    private ProductLogic pl;
    private ProposalLogic prl;

    // #region Constructors

    public Rankings() {
        pl = new ProductLogic();
    }

    // #endregion

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/products")
    public Response getProduct(@QueryParam("supplierId") int supplierId, @QueryParam("amount") int amount, @QueryParam("orderBy") String orderBy) {
        try {
            if (orderBy.equals("proposal"))
                return Response.ok(pl.mostUsedByProposal(supplierId, amount)).build();
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
    @Path("/proposals")
    public Response getProposals(@QueryParam("supplierId") int supplierId, @QueryParam("amount") int amount, @QueryParam("type") String type) {
        try {
            if (type.equals("profit"))
                return Response.ok(prl.getProfitsByProposal(supplierId, amount)).build();
            else
                return Response.status(Status.BAD_REQUEST).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }
}
