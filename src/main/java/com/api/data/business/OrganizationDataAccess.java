package com.api.data.business;

// #region Imports
import com.api.entities.business.Retail;
import com.api.entities.business.Supplier;
import com.api.data.db.Connection;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;

import com.api.entities.business.Organization;

// #endregion

public class OrganizationDataAccess extends BaseDataAccess {
    // #region OrganizationSetup
    public ArrayList<Organization> getOrganizations() {
        ArrayList<Organization> organizations = new ArrayList<Organization>();
        query = "SELECT * FROM Organization;";

        try {
            statement = Connection.getInstancia().getConn().createStatement();

            resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                organizations.add(new Organization(resultSet));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return organizations;
    }

    public Organization getOrganization(int organizationId) {
        Organization organization = null;
        query = "SELECT * FROM Organization WHERE organizationId = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, organizationId);

            resultSet = ((PreparedStatement)statement).executeQuery();

            if (resultSet.next()) {
                organization = new Organization(resultSet);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return organization;
    }

    public Organization createOrganization(Organization organization) {
        query = "INSERT INTO Organization (name, legalName, cuit) VALUES (?, ?, ?);";
        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, organization.getName());
            ((PreparedStatement)statement).setString(2, organization.getLegalName());
            ((PreparedStatement)statement).setString(3, organization.getCuit());
            ((PreparedStatement)statement).executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                organization.setId(resultSet.getInt(1));
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return organization;
    }

    public int updateOrganization(Organization organization) {
        int id = 0;

        query = "UPDATE Organization SET name = ?, legalName = ?, cuit = ? WHERE id = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, organization.getName());
            ((PreparedStatement)statement).setString(2, organization.getLegalName());
            ((PreparedStatement)statement).setString(3, organization.getCuit());

            ((PreparedStatement)statement).executeUpdate();

            resultSet = statement.getGeneratedKeys();

            // If it updated the user, return the updated id
            if (resultSet.next())
                id = resultSet.getInt(1);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return id;
    }
}
