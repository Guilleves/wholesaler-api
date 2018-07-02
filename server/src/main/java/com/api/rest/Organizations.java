package com.api.rest;

// #region Imports

import com.api.entities.models.organization.GetSuppliersResponse;
import javax.ws.rs.QueryParam;
import com.api.entities.models.organization.GetOrganizationsRequest;
import com.api.entities.models.organization.SaveOrganizationRequest;
import com.api.entities.models.organization.GetOrganizationRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.logic.business.OrganizationLogic;

import com.api.logic.validations.ApiException;

import com.api.rest.security.Secured;

// #endregion

@Path("/organizations")
public class Organizations {
    private OrganizationLogic ol;

    // #region Constructors

    public Organizations() {
        ol = new OrganizationLogic();
    }

    // #endregion

    // #region ProductSetup

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response getOrganizations(@QueryParam("role") String role) {
        GetOrganizationsRequest request = new GetOrganizationsRequest();

        request.setRole(role);

        try {
            return Response.ok(ol.getOrganizations(request)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/suppliers")
    public Response getSuppliers() {
        try {
            return Response.ok(ol.getSuppliers()).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/retails")
    public Response getRetails() {
        try {
            return Response.ok(ol.getRetails()).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/{organizationId}")
    public Response getOrganization(@PathParam("organizationId") int organizationId) {
        GetOrganizationRequest request = new GetOrganizationRequest();

        request.setOrganizationId(organizationId);

        try {
            return Response.ok(ol.getOrganization(request)).build();
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
    // On the method parameter, when a payload is provided in the request, it will try to serialize it depending on the consturctor it finds.
    public Response saveOrganization(SaveOrganizationRequest request) {
        try {
            return Response.ok(ol.saveOrganization(request)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    // #endregion
}
