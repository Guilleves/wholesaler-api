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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import java.util.ArrayList;

import com.api.entities.business.User;
import com.api.entities.models.user.AuthenticateUserRequestModel;
import com.api.entities.models.user.AuthenticateUserResponseModel;

import com.api.logic.business.UserLogic;

import com.api.logic.validations.ServerResponse;

import com.api.rest.security.Secured;
import com.api.rest.security.Resource;
import com.api.rest.security.Permission;

// #endregion

@Path("/users")
@Resource(resource=com.api.entities.enums.Resource.USERS)
public class Users {
    private UserLogic ul;

    // #region Constructors

    public Users() {
        ul = new UserLogic();
    }

    // #endregion

    // #region Security

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getUser")
    public User getUser(int userId) {
        User user = ul.getUser(userId);

        return user;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(AuthenticateUserRequestModel request) {
        AuthenticateUserResponseModel response = new AuthenticateUserResponseModel();

        try {
            // Respond with the token
            response = ul.login(request);
            return Response.ok(response).build();
        }
        catch(ServerResponse e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getErrores()).build();
        }
        catch(Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    // #endregion
}
