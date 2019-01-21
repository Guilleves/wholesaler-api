package com.api.data.business;

// #region Imports
import java.sql.Statement;
import com.api.data.db.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.SQLException;

import java.util.ArrayList;

import com.api.entities.business.User;
// #endregion

public class UserDataAccess extends BaseDataAccess {
  // #region UserSetup

  public int countUsers(int organizationId) throws SQLException {
    String query = "SELECT COUNT(*) FROM User WHERE deletedAt IS NULL AND organizationId = ?";

    return getInt(query, organizationId);
  }

  public boolean deleteUser(int id) throws SQLException {
    String query = "UPDATE User SET deletedAt = ? WHERE id = ?";
    Date now = new Date();

    return update(query,
    new java.sql.Timestamp(now.getTime()),
    id) != 0;
  }

  public ArrayList<User> getUsers(String keyword, String orderBy, Integer pageIndex, Integer pageSize, int organizationId) throws SQLException {
    ArrayList<Object> parameters = new ArrayList<Object>();

    String query = "SELECT " +
    "U.*, " +
    "O.id as organizationId, " +
    "O.name as organizationName, " +
    "O.cuit as cuit, " +
    "O.legalName as legalName, " +
    "O.role as role " +
    "FROM User U " +
    "INNER JOIN Organization O on U.organizationId = O.id " +
    "WHERE U.deletedAt IS NULL ";

    if (keyword != null && !keyword.isEmpty()) {
      query += "AND (U.firstName LIKE ? or U.lastName LIKE ? or U.email LIKE ? or U.username LIKE ?) ";
      parameters.add('%' + keyword + '%');
      parameters.add('%' + keyword + '%');
      parameters.add('%' + keyword + '%');
      parameters.add('%' + keyword + '%');
    }

    query += "AND U.organizationId = ? ";
    parameters.add(organizationId);

    if (pageIndex != null && pageSize != null) {
      query += " LIMIT ?, ? ";
      parameters.add(pageIndex * pageSize);
      parameters.add(pageSize);
    }

    query += ";";

    return getMany(rs -> new User(rs), query, parameters.toArray());
  }

  public int countSearch(String keyword, int organizationId) throws SQLException {
    ArrayList<Object> parameters = new ArrayList<Object>();

    String query = "SELECT COUNT(*) FROM User WHERE deletedAt IS NULL ";

    if (keyword != null && !keyword.isEmpty()) {
      query += "AND (firstName LIKE ? or lastName LIKE ? or email LIKE ? or username LIKE ?) ";
      parameters.add('%' + keyword + '%');
      parameters.add('%' + keyword + '%');
      parameters.add('%' + keyword + '%');
      parameters.add('%' + keyword + '%');
    }

    query += "AND organizationId = ? ";
    parameters.add(organizationId);

    query += ";";

    return getInt(query, parameters.toArray());
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
    String query = "INSERT INTO User (firstName, lastName, username, email, password, organizationId) VALUES (?, ?, ?, ?, ?, ?);";

    user.setId(create(query,
    user.getFirstName(),
    user.getLastName(),
    user.getUsername(),
    user.getEmail(),
    user.getPassword(),
    user.getOrganization().getId()
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

  public boolean isAlreadyRegistered(String username, String email) throws SQLException {
    PreparedStatement statement;
    ResultSet resultSet;
    String query = "SELECT * FROM User WHERE username = ? OR email = ?";

    try {
      statement = Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, username);
      statement.setString(2, email);

      resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return true;
      }
    }
    catch(SQLException e) {
      throw e;
    }
    finally {
      Connection.getInstancia().closeConn();
    }

    return false;
  }

  // #endregion
}
