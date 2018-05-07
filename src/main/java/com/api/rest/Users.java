package com.api.rest;

// #region Impor
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.api.entities.models.user.LoginRequest;
import com.api.entities.models.user.GetUserRequest;
import com.api.entities.models.user.SaveUserRequest;

import com.api.logic.business.UserLogic;

import com.api.logic.validations.ApiException;

import com.api.rest.security.Secured;

// #endregion

@Path("/users")
public class Users {
    private UserLogic ul;

    // #region Constructors

    public Users() {
        ul = new UserLogic();
    }

    // #endregion

    // #region UserSetup

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/")
    public Response getUsers() {
        try {
            return Response.ok(ul.getUsers()).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured()
    @Path("/{userId}")
    public Response getUser(@PathParam("userId") int userId) {
        GetUserRequest request = new GetUserRequest(userId, null);

        try {
            return Response.ok(ul.getUser(request)).build();
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
    public Response saveUser(SaveUserRequest request) {
        try {
            ul.saveUser(request);

            return Response.ok().build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    // #endregion

    // #region Security

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(LoginRequest request) {
        try {
            return Response.ok(ul.login(request)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/signup")
    public Response signup(SaveUserRequest request) {
        try {
            return Response.ok(ul.signup(request)).build();
        }
        catch(ApiException e) {
            return Response.status(e.getStatus()).entity(e.getErrors()).build();
        }
    }

    // #endregion
}
