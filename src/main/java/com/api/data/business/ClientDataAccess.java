package com.api.data.business;

// #region Imports
import com.api.data.db.Connection;

import java.sql.SQLException;

import java.util.ArrayList;

import com.api.entities.business.Client;
// #endregion

public class ClientDataAccess extends BaseDataAccess {
    // #region ClientSetup
    public ArrayList<Client> getClients() {
        ArrayList<Client> clients = new ArrayList<Client>();
        query = "SELECT * FROM Clients;";

        try {
            statement = Connection.getInstancia().getConn().createStatement();
            resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                clients.add(new Client(resultSet));
            }
        }
        catch(SQLException e) {

        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return clients;
    }
    // #endregion
}
