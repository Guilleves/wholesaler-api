package com.api.rest;

// #region Imports

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;

import com.api.entities.business.Client;

import com.api.logic.business.ClientLogic;

// #endregion

@Path("/clients")
public class Clients {
    @GET
    @Path("/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClient(@PathParam("clientId") int clientId) {
        Client client = new Client(clientId, "Juan", "Grasso", "Alarcón Muñiz", "490", "3444539608", "Gualeguay", "Entre Ríos");

        return client;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Client> getClients() {
        ClientLogic cl = new ClientLogic();

        return cl.getClients();
    }
}
