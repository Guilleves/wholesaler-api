package com.api.data.business;

// #region Imports

import com.api.entities.enums.OrganizationRoles;
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
                // Organization factory
                if (resultSet.getString("role").equals(OrganizationRoles.SUPPLIER))
                    organizations.add(new Supplier(resultSet));
                else
                    organizations.add(new Retail(resultSet));
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

    public ArrayList<Organization> getOrganizations(String role) {
        ArrayList<Organization> organizations = new ArrayList<Organization>();
        query = "SELECT * FROM Organization WHERE role = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, role);

            resultSet = ((PreparedStatement)statement).executeQuery();

            while(resultSet.next()) {
                // Organization factory
                if (resultSet.getString("role").equals(OrganizationRoles.SUPPLIER))
                    organizations.add(new Supplier(resultSet));
                else
                    organizations.add(new Retail(resultSet));
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
        query = "SELECT * FROM Organization WHERE id = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, organizationId);

            resultSet = ((PreparedStatement)statement).executeQuery();

            if (resultSet.next()) {
                // Organization factory
                if (resultSet.getString("role").equals(OrganizationRoles.SUPPLIER))
                    organization = new Supplier(resultSet);
                else
                    organization = new Retail(resultSet);
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
        query = "INSERT INTO Organization (name, legalName, cuit, role) VALUES (?, ?, ?, ?);";
        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, organization.getName());
            ((PreparedStatement)statement).setString(2, organization.getLegalName());
            ((PreparedStatement)statement).setString(3, organization.getCuit());
            ((PreparedStatement)statement).setString(4, organization.getRole());

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

    // #endregion

}
