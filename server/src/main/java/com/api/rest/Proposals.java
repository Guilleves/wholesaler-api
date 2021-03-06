package com.api.rest;

// #region Imports

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

@Path("/proposals")
public class Proposals {
    private ProposalLogic pl;

    // #region Constructors

    public Proposals() {
        pl = new ProposalLogic();
    }

    // #endregion

    // #region ProposalSetup

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response getProposals(@QueryParam("status") String status, @QueryParam("supplierId") Integer supplierId, @QueryParam("orderBy") String orderBy, @QueryParam("pageSize") Integer pageSize, @QueryParam("pageIndex") Integer pageIndex) {
        GetProposalsRequest request = new GetProposalsRequest(
            orderBy,
            status,
            pageIndex,
            pageSize,
            supplierId
        );

        try {
            return Response.ok(pl.getProposals(request)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/{proposalId}")
    public Response getProposal(@PathParam("proposalId") int proposalId) {
        try {
            return Response.ok(pl.getProposal(proposalId)).build();
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
    public Response saveProposal(@Context SecurityContext context, SaveProposalRequest request) {
        try {
            User user = ((UserPrincipal)context.getUserPrincipal()).getUser();
            return Response.ok(pl.saveProposal(request, user)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/{proposalId}")
    public Response deleteProposal(@PathParam("proposalId") int proposalId, @Context SecurityContext context) {
        try {
            User user = ((UserPrincipal)context.getUserPrincipal()).getUser();
            return Response.ok(pl.deleteProposal(proposalId, user)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    // #endregion
}
