package com.api.data.business;

// #region Imports
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

    public Organization getOrganization(String cuit) {
        Organization organization = null;
        query = "SELECT * FROM Organization WHERE cuit = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, cuit);

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

    public boolean saveOrganization(Organization organization) {
        query = "INSERT INTO Organization (name, legalName, cuit) VALUES (?, ?, ?);";
        int updatedFields = 0;
        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, organization.getName());
            ((PreparedStatement)statement).setString(2, organization.getLegalName());
            ((PreparedStatement)statement).setString(3, organization.getCuit());

            updatedFields = ((PreparedStatement)statement).executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return (updatedFields > 0);
    }
}
