package com.api.data.business;

// #region Imports

import com.api.entities.business.Ranking;
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

    public ArrayList<Ranking> getProfitsByRetail(int supplierId, int amount) throws SQLException {
        ArrayList<Integer> parameters = new ArrayList<Integer>();

        String query = "SELECT " +
        "O.id as id, " +
        "O.name as name, "+
        "O.cuit as cuit, " +
        "O.legalName as legalName, " +
        "O.role as role, " +
        "SUM(PL.price * OL.quantity) as `sum` " +
        "FROM " +
        "Organization O " +
        "INNER JOIN `Order` Ord ON O.id = Ord.retailId " +
        "INNER JOIN OrderLine OL ON OL.orderId = Ord.id " +
        "INNER JOIN ProposalLine PL ON PL.id = OL.proposalLineId " +
        "INNER JOIN Proposal Pr ON Pr.id = PL.proposalId " +
        "WHERE Pr.deletedAt IS NULL ";

        if (supplierId != 0) {
            query += "AND Pr.supplierId = ? ";
            parameters.add(supplierId);
        }
        query += "GROUP BY O.id " +
        "ORDER BY SUM(PL.price * OL.quantity) DESC ";

        if (amount != 0) {
            query += "LIMIT ?";
            parameters.add(amount);
        }

        query += ";";

        System.out.println(query);

        return getMany(rs -> deserializeRanking(rs), query, parameters.toArray());
    }

    private Ranking deserializeRanking(ResultSet rs) throws SQLException {
        return new Ranking(rs.getFloat("sum"), new Retail(rs));
    }

    private Organization deserializeOrganization(ResultSet rs) throws SQLException {
        if (rs.getString("role").equals(OrganizationRoles.SUPPLIER))
            return new Supplier(rs);
        else
            return new Retail(rs);
    }

}
