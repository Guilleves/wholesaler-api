package com.api.data.business;

// #region Imports
import java.sql.SQLException;

import java.util.ArrayList;

import com.api.entities.business.User;
// #endregion

public class UserDataAccess extends BaseDataAccess {
    // #region UserSetup

    public int countUsers() throws SQLException {
      String query = "SELECT COUNT(*) FROM User WHERE deletedAt IS NULL";

      return getInt(query);
    }

    public ArrayList<User> getUsers() throws SQLException {
        String query = "SELECT " +
            "U.*, " +
            "O.id as organizationId, " +
            "O.name as organizationName, " +
            "O.cuit as cuit, " +
            "O.legalName as legalName, " +
            "O.role as role " +
            "FROM User U " +
            "INNER JOIN Organization O on U.organizationId = O.id;";

        return getMany(rs -> new User(rs), query);
    }

    public User getUser(int id) throws SQLException {
        String query = "SELECT " +
            "U.*, " +
            "O.id as organizationId, " +
            "O.name as organizationName, " +
            "O.cuit as cuit, " +
            "O.legalName as legalName, " +
            "O.role as role " +
            "FROM User U " +
            "INNER JOIN Organization O on U.organizationId = O.id " +
            "WHERE U.id = ?;";

        return getOne(rs -> new User(rs), query, id);
    }

    public User getUser(String username) throws SQLException {
        String query = "SELECT " +
            "U.*, " +
            "O.id as organizationId, " +
            "O.name as organizationName, " +
            "O.cuit as cuit, " +
            "O.legalName as legalName, " +
            "O.role as role " +
            "FROM User U " +
            "INNER JOIN Organization O on U.organizationId = O.id " +
            "WHERE U.username = ?;";

            return getOne(rs -> new User(rs), query, username);
    }

    public User createUser(User user) throws SQLException {
        String query = "INSERT INTO User (firstName, lastName, username, email, password) VALUES (?, ?, ?, ?, ?);";

        user.setId(create(query,
            user.getFirstName(),
            user.getLastName(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword()
        ));

        return user;
    }

    public int updateUser(User user) throws SQLException {
        String query = "UPDATE User SET firstName = ?, lastName = ?, username = ?, email = ?, password = ? WHERE id = ?;";

        return update(query,
            user.getFirstName(),
            user.getLastName(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getId()
        );
    }

    public ArrayList<User> getUsersByOrganization(int organizationId) throws SQLException {
        String query = "SELECT * FROM User u WHERE u.organizationId = ?;";

        return getMany(rs -> new User(rs), query, organizationId);
    }

    // #endregion
}
