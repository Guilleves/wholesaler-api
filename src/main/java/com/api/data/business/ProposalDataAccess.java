package com.api.data.business;

import com.api.entities.business.Supplier;
import java.util.Date;
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

    // #region Proposal setup

    public Proposal getProposal(int proposalId) {
        Proposal proposal = null;

        // Eeeeeeeeeeeeeeeeeek...
        query = "SELECT " +
            "P.*, " +
            "O.id as organizationId, " +
            "O.name as organizationName, " +
            "O.cuit, " +
            "O.legalName, " +
            "O.role, " +
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
            "INNER JOIN Organization O ON P.supplierId = O.id " +
            "INNER JOIN ProposalLine PL ON P.id = PL.proposalId " +
            "INNER JOIN Product Pr ON PL.productId = Pr.id " +
            "INNER JOIN Brand B ON Pr.brandId = B.id " +
            "INNER JOIN Category C ON Pr.categoryId = C.id " +
            "WHERE P.id = ? " +
            "AND P.deletedAt IS NULL " +
            "AND PL.deletedAt IS NULL;";

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

    public ArrayList<Proposal> getProposals(String status, Integer supplierId) {
        ArrayList<Proposal> proposals = new ArrayList<Proposal>();

        // qué lindo el multiline stringNO
        query = "SELECT P.*, O.id as organizationId, O.name as organizationName, O.cuit, O.legalName, O.role, PL.id as proposalLineId, PL.price as price, Pr.id as productId, Pr.name as productName, Pr.gtin as gtin, B.id as brandId, B.name as brandName, C.id as categoryId, C.name as categoryName FROM Proposal P INNER JOIN Organization O ON P.supplierId = O.id INNER JOIN ProposalLine PL ON P.id = PL.proposalId INNER JOIN Product Pr ON PL.productId = Pr.id INNER JOIN Brand B ON Pr.brandId = B.id INNER JOIN Category C ON Pr.categoryId = C.id WHERE P.deletedAt IS NULL AND PL.deletedAt IS NULL";

        if (status != null) {
            switch (status) {
                case "active": query = query.concat(" AND P.beginDate <= now() and P.endDate <= now");
                case "finished": query = query.concat(" AND P.beginDate <= now() and P.endDate > now") ;
                case "scheduled": query = query.concat(" AND P.beginDate > now()");
                default: break;
            };
        };
        if (supplierId != null) {
            query = query.concat(" and P.supplierId = ") + supplierId;
        };
        query = query.concat(";");

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

    public Proposal deleteProposal(Proposal proposal) {
        query = "UPDATE Proposal SET deletedAt = ? WHERE id = ?;";
        Date now = new Date();

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setTimestamp(1, new java.sql.Timestamp(now.getTime()));
            ((PreparedStatement)statement).setInt(2, proposal.getId());

            int editionAmt = ((PreparedStatement)statement).executeUpdate();

            if (editionAmt == 1)
                proposal.setDeletedAt(now);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return proposal;
    }

    public ProposalLine deleteProposalLine(ProposalLine proposalLine) {
        query = "UPDATE Proposal SET deletedAt = ? WHERE id = ?;";
        Date now = new Date();

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setTimestamp(1, new java.sql.Timestamp(now.getTime()));
            ((PreparedStatement)statement).setInt(2, proposalLine.getId());

            int editionAmt = ((PreparedStatement)statement).executeUpdate();

            if (editionAmt == 1)
                proposalLine.setDeletedAt(now);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return proposalLine;
    }

    public Proposal registerProposal(Proposal proposal) {
        java.sql.Connection conn = Connection.getInstancia().getConn();

        try {
            conn.setAutoCommit(false);

            // All this messy code is to run everything as a transaction.
            proposal = createProposal(proposal);

            for (ProposalLine pl : proposal.getProposalLines()) {
                // TODO: IDK other way to avoid infinite loops, at least this way it works.
                Proposal p = new Proposal();
                p.setId(proposal.getId());
                pl.setProposal(p);

                // Would this do the trick? Damn java, you tha boss.
                pl = createProposalLine(pl);
            }

            conn.commit();
            conn.setAutoCommit(true);
        }
        catch(SQLException e1) {
            try {
                e1.printStackTrace();
                conn.rollback();
                conn.setAutoCommit(true);
            }
            catch(SQLException e2) {
                e2.printStackTrace();
            }
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return proposal;
    }

    public Proposal createProposal(Proposal proposal) {
        query = "INSERT INTO Proposal (beginDate, endDate, description, supplierId, title) VALUES (?, ?, ?, ?, ?);";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setTimestamp(1, new java.sql.Timestamp(proposal.getBeginDate().getTime()));
            ((PreparedStatement)statement).setTimestamp(2, new java.sql.Timestamp(proposal.getEndDate().getTime()));
            ((PreparedStatement)statement).setString(3, proposal.getDescription());
            ((PreparedStatement)statement).setInt(4, proposal.getSupplier().getId());
            ((PreparedStatement)statement).setString(5, proposal.getTitle());


            ((PreparedStatement)statement).executeUpdate();

            resultSet = statement.getGeneratedKeys();

            // If it created the user, return the created id
            if (resultSet.next()) {
                proposal.setId(resultSet.getInt(1));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return proposal;
    }

    public ProposalLine createProposalLine(ProposalLine proposalLine) {
        query = "INSERT INTO ProposalLine (proposalId, productId, price) VALUES (?, ?, ?);";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, proposalLine.getProposal().getId());
            ((PreparedStatement)statement).setInt(2, proposalLine.getProduct().getId());
            ((PreparedStatement)statement).setFloat(3, proposalLine.getPrice());

            ((PreparedStatement)statement).executeUpdate();

            resultSet = statement.getGeneratedKeys();

            // If it created the user, return the created id
            if (resultSet.next()) {
                proposalLine.setId(resultSet.getInt(1));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return proposalLine;
    }


    // #endregion

    // #region Privates

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
                proposal.setTitle(resultSet.getString("title"));
                proposal.setSupplier(new Supplier(
                    resultSet.getInt("organizationId"),
                    resultSet.getString("organizationName"),
                    resultSet.getString("cuit"),
                    resultSet.getString("legalName"),
                    resultSet.getString("role")
                ));

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
                proposal.setTitle(resultSet.getString("title"));
                proposal.setSupplier(new Supplier(
                    resultSet.getInt("organizationId"),
                    resultSet.getString("organizationName"),
                    resultSet.getString("cuit"),
                    resultSet.getString("legalName"),
                    resultSet.getString("role")
                ));

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

    // #endregion
}
