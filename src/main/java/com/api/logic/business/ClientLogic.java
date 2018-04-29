package com.api.logic.business;

// #region Imports

import java.util.ArrayList;

import com.api.entities.business.Client;

import com.api.data.business.ClientDataAccess;

// #endregion

public class ClientLogic {
    // #region ClientSetup
    public ArrayList<Client> getClients() {
        ClientDataAccess cda = new ClientDataAccess();

        return cda.getClients();
    }

    // #endregion
}
