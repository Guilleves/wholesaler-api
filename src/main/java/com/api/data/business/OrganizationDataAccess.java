package com.api.data.business;

// #region Imports

import java.sql.ResultSet;
import com.api.entities.enums.OrganizationRoles;
import com.api.entities.business.Retail;
import com.api.entities.business.Supplier;

import java.sql.SQLException;

import java.util.ArrayList;

import com.api.entities.business.Organization;

// #endregion

public class OrganizationDataAccess extends BaseDataAccess {

    // #region OrganizationSetup

    public ArrayList<Organization> getOrganizations() throws SQLException {
        String query = "SELECT * FROM Organization;";

        return getMany(rs -> deserializeOrganization(rs), query);
    }

    public ArrayList<Organization> getOrganizations(String role) throws SQLException {
        String query = "SELECT * FROM Organization WHERE role = ?;";

        return getMany(rs -> deserializeOrganization(rs), query, role);
    }

    public Organization getOrganization(int organizationId) throws SQLException {
        String query = "SELECT * FROM Organization WHERE id = ?;";

        return getOne(rs -> deserializeOrganization(rs), query, organizationId);
    }

    public Organization createOrganization(Organization organization) throws SQLException{
        String query = "INSERT INTO Organization (name, legalName, cuit, role) VALUES (?, ?, ?, ?);";

        organization.setId(create(query,
            organization.getName(),
            organization.getLegalName(),
            organization.getCuit(),
            organization.getRole()
        ));

        return organization;
    }

    public int updateOrganization(Organization organization) throws SQLException {
        String query = "UPDATE Organization SET name = ?, legalName = ?, cuit = ? WHERE id = ?;";

        return update(query,
            organization.getName(),
            organization.getLegalName(),
            organization.getCuit(),
            organization.getRole()
        );
    }

    // #endregion

    private Organization deserializeOrganization(ResultSet rs) throws SQLException {
        if (rs.getString("role").equals(OrganizationRoles.SUPPLIER))
            return new Supplier(rs);
        else
            return new Retail(rs);
    }

}
