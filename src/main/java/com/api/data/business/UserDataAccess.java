package com.api.data.business;

// #region Imports
import com.api.data.db.Connection;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;

import com.api.entities.business.User;
// #endregion

public class UserDataAccess extends BaseDataAccess {
    // #region UserSetup

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();

        query = "SELECT " +
            "U.*, " +
            "O.id as organizationId, " +
            "O.name as organizationName, " +
            "O.cuit as cuit, " +
            "O.legalName as legalName, " +
            "O.role as role " +
            "FROM User U " +
            "INNER JOIN Organization O on U.organizationId = O.id;";

        try {
            statement = Connection.getInstancia().getConn().createStatement();

            resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                users.add(new User(resultSet));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return users;
    }

    public User getUser(int id) {
        User user = null;

        query = "SELECT " +
            "U.*, " +
            "O.id as organizationId, " +
            "O.name as organizationName, " +
            "O.cuit as cuit, " +
            "O.legalName as legalName, " +
            "O.role as role " +
            "FROM User U " +
            "INNER JOIN Organization O on U.organizationId = O.id " +
            "WHERE U.id = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, id);

            resultSet = ((PreparedStatement)statement).executeQuery();

            if (resultSet.next()) {
                user = new User(resultSet);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return user;
    }

    public User getUser(String username) {
        User user = null;

        query = "SELECT " +
            "U.*, " +
            "O.id as organizationId, " +
            "O.name as organizationName, " +
            "O.cuit as cuit, " +
            "O.legalName as legalName, " +
            "O.role as role " +
            "FROM User U " +
            "INNER JOIN Organization O on U.organizationId = O.id " +
            "WHERE U.username = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, username);

            resultSet = ((PreparedStatement)statement).executeQuery();

            if (resultSet.next()) {
                user = new User(resultSet);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return user;
    }

    public int createUser(User user) {
        int id = 0;

        query = "INSERT INTO User (firstName, lastName, username, email, password) VALUES (?, ?, ?, ?, ?);";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, user.getFirstName());
            ((PreparedStatement)statement).setString(2, user.getLastName());
            ((PreparedStatement)statement).setString(3, user.getUsername());
            ((PreparedStatement)statement).setString(4, user.getEmail());
            ((PreparedStatement)statement).setString(5, user.getPassword());

            ((PreparedStatement)statement).executeUpdate();

            resultSet = statement.getGeneratedKeys();

            // If it created the user, return the created id
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return id;
    }

    public int updateUser(User user) {
        int id = 0;

        query = "UPDATE User SET firstName = ?, lastName = ?, username = ?, email = ?, password = ? WHERE id = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, user.getFirstName());
            ((PreparedStatement)statement).setString(2, user.getLastName());
            ((PreparedStatement)statement).setString(3, user.getUsername());
            ((PreparedStatement)statement).setString(4, user.getEmail());
            ((PreparedStatement)statement).setString(5, user.getPassword());
            ((PreparedStatement)statement).setInt(6, user.getId());

            ((PreparedStatement)statement).executeUpdate();

            resultSet = statement.getGeneratedKeys();

            // If it updated the user, return the updated id
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return id;
    }

    public ArrayList<User> getUsersByOrganization(int organizationId) {
        ArrayList<User> users = new ArrayList<User>();
        query = "SELECT * FROM User u WHERE u.organizationId = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, organizationId);

            resultSet = ((PreparedStatement)statement).executeQuery();

            while(resultSet.next()) {
                users.add(new User(resultSet));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return users;
    }

    // #endregion
}
