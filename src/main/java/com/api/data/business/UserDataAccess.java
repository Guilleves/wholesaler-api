package com.api.data.business;

// #region Imports
import com.api.data.db.Connection;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import com.api.entities.business.User;
import com.api.entities.business.AuthToken;
import com.api.entities.business.Role;
import com.api.entities.business.Resource;
import com.api.entities.business.Permission;
// #endregion

public class UserDataAccess extends BaseDataAccess {
    // #region UserSetup
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        query = "SELECT * FROM Users;";

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

    public User getUser(String username) {
        User user = null;
        query = "SELECT * FROM Users WHERE username = ?;";

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

    public User getUser(int userId) {
        User user = null;
        query = "SELECT "+
        "* "+
        "FROM "+
        "Users U "+
        "INNER JOIN UsersRoles UR ON U.id = UR.userId "+
        "INNER JOIN Roles R ON R.id = UR.roleId "+
        "INNER JOIN RolesResourcesPermissions RRP ON RRP.roleId = R.id "+
        "INNER JOIN ResourcesPermissions RP ON RP.id = RRP.resourcePermissionId "+
        "INNER JOIN Resources Res ON Res.id = RP.resourceId "+
        "INNER JOIN Permissions Per ON Per.id = RP.permissionId "+
        "WHERE "+
        "U.id = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, userId);

            resultSet = ((PreparedStatement)statement).executeQuery();

            // Todos los id que voy agregando para armar mi user los buffereo.
            int currentUserId = 0;
            ArrayList<Integer> roles = new ArrayList<Integer>();
            ArrayList<Integer> resources = new ArrayList<Integer>();
            ArrayList<Integer> permissions = new ArrayList<Integer>();
            Role role = null;
            Resource resource = null;

            while (resultSet.next()) {
                // Si es un user que ya guardé no vuelvo a crear otra entidad
                if (currentUserId != resultSet.getInt("U.id")) {
                    user = new User();

                    user.setId(resultSet.getInt("U.id"));
                    user.setUsername(resultSet.getString("U.username"));
                    user.setPassword(resultSet.getString("U.password"));
                    user.setFirstName(resultSet.getString("U.firstName"));
                    user.setLastName(resultSet.getString("U.lastName"));
                    user.setPrimaryAddress1(resultSet.getString("U.primaryAddress1"));
                    user.setPrimaryAddress2(resultSet.getString("U.primaryAddress2"));
                    user.setCity(resultSet.getString("U.city"));
                    user.setProvince(resultSet.getString("U.province"));
                    user.setCountry(resultSet.getString("U.country"));
                    user.setPrimaryPhone(resultSet.getString("U.primaryPhone"));
                    user.setSecondaryPhone(resultSet.getString("U.secondaryPhone"));
                    user.setEmail(resultSet.getString("U.email"));
                    user.setBirthdate(resultSet.getDate("U.birthdate"));

                    currentUserId = user.getId();
                }

                // Si es un rol que ya guardé no vuelvo a crear otra entidad
                if (!roles.contains(resultSet.getInt("R.id"))) {
                    role = new Role();

                    role.setId(resultSet.getInt("R.id"));
                    role.setRoleName(resultSet.getString("R.roleName"));

                    user.addRole(role);

                    // Agrego el id al buffer de roles
                    roles.add(role.getId());
                }

                if (!resources.contains(resultSet.getInt("Res.id"))) {
                    resource = new Resource();

                    resource.setId(resultSet.getInt("Res.id"));
                    resource.setResourceName(resultSet.getString("Res.resourceName"));

                    role.addResource(resource);

                    resources.add(resource.getId());
                }

                // Lo mismo para los permisos y recursos
                if (!permissions.contains(resultSet.getInt("Per.id"))) {
                    Permission permission = new Permission();

                    permission.setId(resultSet.getInt("Per.id"));
                    permission.setPermissionName(resultSet.getString("Per.permissionName"));

                    resource.addPermission(permission);

                    // Agrego el id al buffer de permisos y recursos
                    permissions.add(permission.getId());
                }
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

    public boolean saveUser(User user) {
        return true;
    }

    // #endregion

    // #region Security
    public User getUserWithToken(String token) {
        User user = null;
        query = "SELECT "+
        "* "+
        "FROM "+
        "Users U "+
        "INNER JOIN AuthTokens AT ON AT.userId = U.id "+
        "INNER JOIN UsersRoles UR ON U.id = UR.userId "+
        "INNER JOIN Roles R ON R.id = UR.roleId "+
        "INNER JOIN RolesResourcesPermissions RRP ON RRP.roleId = R.id "+
        "INNER JOIN ResourcesPermissions RP ON RP.id = RRP.resourcePermissionId "+
        "INNER JOIN Resources Res ON Res.id = RP.resourceId "+
        "INNER JOIN Permissions Per ON Per.id = RP.permissionId "+
        "WHERE "+
        "AT.token = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, token);

            resultSet = ((PreparedStatement)statement).executeQuery();

            // Todos los id que voy agregando para armar mi user los buffereo.
            int currentUserId = 0;
            ArrayList<Integer> roles = new ArrayList<Integer>();
            ArrayList<Integer> resources = new ArrayList<Integer>();
            ArrayList<Integer> permissions = new ArrayList<Integer>();
            Role role = null;
            Resource resource = null;

            while (resultSet.next()) {
                // Si es un user que ya guardé no vuelvo a crear otra entidad
                if (currentUserId != resultSet.getInt("U.id")) {
                    user = new User();

                    user.setId(resultSet.getInt("U.id"));
                    user.setUsername(resultSet.getString("U.username"));
                    user.setPassword(resultSet.getString("U.password"));
                    user.setFirstName(resultSet.getString("U.firstName"));
                    user.setLastName(resultSet.getString("U.lastName"));
                    user.setPrimaryAddress1(resultSet.getString("U.primaryAddress1"));
                    user.setPrimaryAddress2(resultSet.getString("U.primaryAddress2"));
                    user.setCity(resultSet.getString("U.city"));
                    user.setProvince(resultSet.getString("U.province"));
                    user.setCountry(resultSet.getString("U.country"));
                    user.setPrimaryPhone(resultSet.getString("U.primaryPhone"));
                    user.setSecondaryPhone(resultSet.getString("U.secondaryPhone"));
                    user.setEmail(resultSet.getString("U.email"));
                    user.setBirthdate(resultSet.getDate("U.birthdate"));

                    currentUserId = user.getId();
                }

                // Si es un rol que ya guardé no vuelvo a crear otra entidad
                if (!roles.contains(resultSet.getInt("R.id"))) {
                    role = new Role();

                    role.setId(resultSet.getInt("R.id"));
                    role.setRoleName(resultSet.getString("R.roleName"));

                    user.addRole(role);

                    // Agrego el id al buffer de roles
                    roles.add(role.getId());
                }

                // Lo mismo para los permisos y recursos
                if (!resources.contains(resultSet.getInt("Res.id"))) {
                    resource = new Resource();

                    resource.setId(resultSet.getInt("Res.id"));
                    resource.setResourceName(resultSet.getString("Res.resourceName"));

                    role.addResource(resource);

                    resources.add(resource.getId());
                }

                if (!permissions.contains(resultSet.getInt("Per.id"))) {
                    Permission permission = new Permission();

                    permission.setId(resultSet.getInt("Per.id"));
                    permission.setPermissionName(resultSet.getString("Per.permissionName"));

                    resource.addPermission(permission);

                    // Agrego el id al buffer de permisos y recursos
                    permissions.add(permission.getId());
                }
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

    public void createAuthToken(int userId, String token) {
        // Default value of created timestamp is current date.
        query = "INSERT INTO AuthTokens(userId, token) VALUES (?, ?);";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, userId);
            ((PreparedStatement)statement).setString(2, token);

            ((PreparedStatement)statement).executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }
    }

    public void refreshAuthToken(String prevToken, String newToken, Date date) {
        // Default value of created timestamp is current date.
        query = "UPDATE AuthTokens SET token = ?, date = ? WHERE token = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, prevToken);
            ((PreparedStatement)statement).setDate(2, new java.sql.Date(date.getTime()));
            ((PreparedStatement)statement).setString(3, newToken);

            ((PreparedStatement)statement).executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }
    }

    public AuthToken getAuthToken(String token) {
        AuthToken authToken = null;
        query = "SELECT * FROM AuthTokens WHERE token = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, token);

            resultSet = ((PreparedStatement)statement).executeQuery();

            if (resultSet.next()) {
                authToken = new AuthToken(resultSet);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return authToken;
    }

    // #endregion
}
