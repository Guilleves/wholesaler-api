package com.api.data.business;

import java.util.HashMap;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.api.entities.business.ProposalLine;
import com.api.entities.business.Product;
import com.api.entities.business.Category;
import com.api.entities.business.Brand;
import java.sql.Statement;
import java.sql.SQLException;
import com.api.data.db.Connection;
import java.sql.PreparedStatement;
import com.api.entities.business.Proposal;

public class ProposalDataAccess extends BaseDataAccess {
    public Proposal getProposal(int proposalId) {
        Proposal proposal = null;

        // Eeeeeeeeeeeeeeeeeek...
        query = "SELECT " +
            "P.*, " +
            "PL.price as price, " +
            "PL.id as proposalLineId, " +
            "Pr.id as productId, " +
            "Pr.name as productName, " +
            "Pr.gtin as gtin, " +
            "B.id as brandId, " +
            "B.name as brandName, " +
            "C.id as categoryId, " +
            "C.name as categoryName " +
            "FROM " +
            "Proposal P " +
            "INNER JOIN ProposalLine PL ON P.id = PL.proposalId " +
            "INNER JOIN Product Pr ON PL.productId = Pr.id " +
            "INNER JOIN Brand B ON Pr.brandId = B.id " +
            "INNER JOIN Category C ON Pr.categoryId = C.id " +
            "WHERE P.id = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, proposalId);

            resultSet = ((PreparedStatement)statement).executeQuery();

            proposal = deserializeProposal(resultSet);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return proposal;
    }

    public ArrayList<Proposal> getProposals() {
        ArrayList<Proposal> proposals = new ArrayList<Proposal>();

        // Eeeeeeeeeeeeeeeeeek...
        // TODO: Ask @guilleves
        // Should I inner join? or left join? Are we always generating lines or could empty proposals exist?
        query = "SELECT " +
            "P.*, " +
            "PL.id as proposalLineId, " +
            "PL.price as price, " +
            "Pr.id as productId, " +
            "Pr.name as productName, " +
            "Pr.gtin as gtin, " +
            "B.id as brandId, " +
            "B.name as brandName, " +
            "C.id as categoryId, " +
            "C.name as categoryName " +
            "FROM " +
            "Proposal P " +
            "INNER JOIN ProposalLine PL ON P.id = PL.proposalId " +
            "INNER JOIN Product Pr ON PL.productId = Pr.id " +
            "INNER JOIN Brand B ON Pr.brandId = B.id " +
            "INNER JOIN Category C ON Pr.categoryId = C.id;";

        try {
            statement = Connection.getInstancia().getConn().createStatement();

            resultSet = statement.executeQuery(query);

            proposals = deserializeProposals(resultSet);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return proposals;
    }

    private Proposal deserializeProposal(ResultSet resultSet) throws SQLException {
        Proposal proposal = new Proposal();

        int currentProposalId = 0;

        // There are more than 1 result, due to the inner join *EEEEEEEEK*
        while (resultSet.next()) {
            // If proposal didn't change (should be only one... but just in case)
            if (resultSet.getInt("id") != currentProposalId) {
                proposal.setId(resultSet.getInt("id"));
                proposal.setBeginDate(resultSet.getTimestamp("beginDate"));
                proposal.setEndDate(resultSet.getTimestamp("endDate"));
                proposal.setDescription(resultSet.getString("description"));

                currentProposalId = proposal.getId();
            }

            // Add all the lines with the products to the proposal.
            ProposalLine line = new ProposalLine();

            line.setPrice(resultSet.getFloat("price"));
            line.setId(resultSet.getInt("proposalLineId"));

            line.setProduct(new Product(
                resultSet.getInt("productId"),
                resultSet.getString("productName"),
                resultSet.getString("gtin"),
                new Brand(resultSet.getInt("brandId"), resultSet.getString("brandName")),
                new Category(resultSet.getInt("categoryId"), resultSet.getString("categoryName"))
            ));

            proposal.getProposalLines().add(line);
        }

        return proposal;
    }

    private ArrayList<Proposal> deserializeProposals(ResultSet resultSet) throws SQLException {
        Proposal proposal = null;
        HashMap<Integer, Proposal> proposals = new HashMap<Integer, Proposal>();

        // There are more than 1 result, due to the inner join *EEEEEEEEK*
        while (resultSet.next()) {
            proposal = proposals.get(resultSet.getInt("id"));

            // When proposal changes, add it to the arrayList and start filling it.
            if (proposal == null) {
                proposal = new Proposal();

                proposal.setId(resultSet.getInt("id"));
                proposal.setBeginDate(resultSet.getTimestamp("beginDate"));
                proposal.setEndDate(resultSet.getTimestamp("endDate"));
                proposal.setDescription(resultSet.getString("description"));

                proposals.put(proposal.getId() ,proposal);
            }

            // Lines and products.
            ProposalLine line = new ProposalLine();

            line.setPrice(resultSet.getFloat("price"));
            line.setId(resultSet.getInt("proposalLineId"));

            line.setProduct(new Product(
                resultSet.getInt("productId"),
                resultSet.getString("productName"),
                resultSet.getString("gtin"),
                new Brand(resultSet.getInt("brandId"), resultSet.getString("brandName")),
                new Category(resultSet.getInt("categoryId"), resultSet.getString("categoryName"))
            ));

            proposal.getProposalLines().add(line);
        }

        return new ArrayList<Proposal>(proposals.values());
    }

    public ArrayList<Proposal> getProposalsBySupplier(int organizationId) {
        ArrayList<Proposal> proposals = null;
        query = "SELECT P.*, PL.id as proposalLineId, PL.price as price, Pr.id as productId, Pr.name as productName, Pr.gtin as gtin, B.id as brandId, B.name as brandName, C.id as categoryId, C.name as categoryName FROM Proposal P INNER JOIN ProposalLine PL ON P.id = PL.proposalId INNER JOIN Product Pr ON PL.productId = Pr.id INNER JOIN Brand B ON Pr.brandId = B.id INNER JOIN Category C ON Pr.categoryId = C.id WHERE P.supplier = ? ;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, organizationId);

            resultSet = ((PreparedStatement)statement).executeQuery();

            proposals = deserializeProposals(resultSet);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return proposals;
    }
}
