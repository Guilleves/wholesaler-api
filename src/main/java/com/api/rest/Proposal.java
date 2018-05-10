package com.api.rest;

// #region Imports

import com.api.entities.models.proposal.SaveProposalRequest;
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

import com.api.entities.models.proposal.GetProposalRequest;

import com.api.logic.business.ProposalLogic;

import com.api.logic.validations.ApiException;

import com.api.rest.security.Secured;

// #endregion

@Path("/proposals")
public class Proposal {
    private ProposalLogic pl;

    // #region Constructors

    public Proposal() {
        pl = new ProposalLogic();
    }

    // #endregion

    // #region ProposalSetup

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response getProposals() {
        try {
            return Response.ok(pl.getProposals()).build();
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
    public Response getProduct(@PathParam("proposalId") int proposalId) {
        GetProposalRequest request = new GetProposalRequest(proposalId);

        try {
            return Response.ok(pl.getProposal(request)).build();
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
            return Response.ok(pl.saveProposal(request, (UserPrincipal)context.getUserPrincipal())).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    // #endregion
}
