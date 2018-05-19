package com.api.data.business;

import com.api.entities.enums.ProposalStates;
import com.api.entities.business.Supplier;
import java.util.Date;
import java.util.HashMap;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.api.entities.business.ProposalLine;
import com.api.entities.business.Product;
import com.api.entities.business.Category;
import com.api.entities.business.Brand;
import java.sql.SQLException;
import com.api.data.db.Connection;
import com.api.entities.business.Proposal;

public class ProposalDataAccess extends BaseDataAccess {

    // #region Proposal setup

    public Proposal getProposal(int proposalId) throws SQLException {
        // Eeeeeeeeeeeeeeeeeek...
        String query = "SELECT " +
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

        return getOneWithoutStatement(rs -> deserializeProposal(rs), query, proposalId);
    }

    public ArrayList<Proposal> getProposals(String status, Integer supplierId, String orderBy, Integer pageSize, Integer pageIndex) throws SQLException {
        // Works only when there's at least one row (document) of each table (collection) because of the inner join...
        String query = "SELECT P.*, O.id as organizationId, O.name as organizationName, O.cuit, O.legalName, O.role, PL.id as proposalLineId, PL.price as price, Pr.id as productId, Pr.name as productName, Pr.gtin as gtin, B.id as brandId, B.name as brandName, C.id as categoryId, C.name as categoryName FROM Proposal P INNER JOIN Organization O ON P.supplierId = O.id INNER JOIN ProposalLine PL ON P.id = PL.proposalId INNER JOIN Product Pr ON PL.productId = Pr.id INNER JOIN Brand B ON Pr.brandId = B.id INNER JOIN Category C ON Pr.categoryId = C.id WHERE P.deletedAt IS NULL AND PL.deletedAt IS NULL";

        if (status != null) {
            switch (status) {
                case ProposalStates.ACTIVE:
                    query = query.concat(" AND P.beginDate <= now() and P.endDate >= now()");
                    break;
                case ProposalStates.FINISHED:
                    query = query.concat(" AND P.endDate < now()") ;
                    break;
                case ProposalStates.SCHEDULED:
                    query = query.concat(" AND P.beginDate > now()");
                    break;
                default:
                    break;
            };
        }

        if (supplierId != null)
            query += " AND P.supplierId = ?";

        if (orderBy != null)
            query += " ORDER BY ?";

        if (pageSize != null && pageIndex != null)
            query += " LIMIT ?, ?";

        query = query.concat(";");

        ArrayList<Object> parameters = new ArrayList<Object>();

        if (supplierId != null) {
            parameters.add(supplierId);
        }

        if (orderBy != null) {
            parameters.add(orderBy);
        }

        if (pageSize != null && pageIndex != null) {
            parameters.add(pageIndex);
            parameters.add(pageSize);
        }

        return getManyWithoutStatement(rs -> deserializeProposals(rs), query, parameters.toArray());
    }

    public ProposalLine getProposalLine(int proposalLineId) throws SQLException {
        String query = "SELECT " +
        "PL.*, " +
        "P.name as productName, " +
        "P.gtin, " +
        "O.id as supplierId, " +
        "O.name as supplierName, " +
        "O.cuit, " +
        "O.legalName, " +
        "O.role, " +
        "Pr.title, " +
        "Pr.description, " +
        "Pr.beginDate, " +
        "Pr.endDate, " +
        "B.id as brandId, " +
        "B.name as brandName, " +
        "C.id as categoryId, " +
        "C.name as categoryName " +
        "FROM " +
        "ProposalLine PL " +
        "INNER JOIN Proposal Pr ON Pr.id = PL.proposalId " +
        "INNER JOIN Organization O ON Pr.supplierId = O.id " +
        "INNER JOIN Product P ON P.id = PL.productId " +
        "INNER JOIN Brand B ON P.brandId = B.id " +
        "INNER JOIN Category C ON P.categoryId = C.id " +
        "WHERE PL.id = ?";

        return getOneWithoutStatement(rs -> deserializeProposalLine(rs), query, proposalLineId);
    }

    public boolean deleteProposal(int proposalId) throws SQLException {
        String query = "UPDATE Proposal SET deletedAt = ? WHERE id = ?;";
        Date now = new Date();

        return update(query,
            new java.sql.Timestamp(now.getTime()),
            proposalId
        ) != 0;
    }

    public boolean deleteProposalLine(int proposalLineId) throws SQLException{
        String query = "UPDATE Proposal SET deletedAt = ? WHERE id = ?;";
        Date now = new Date();

        return update(query,
            new java.sql.Timestamp(now.getTime()),
            proposalLineId
        ) != 0;
    }

    public Proposal registerProposal(Proposal proposal) throws SQLException {
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
                conn.rollback();
                conn.setAutoCommit(true);
                throw e1;
            }
            catch(SQLException e2) {
                throw e2;
            }
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return proposal;
    }

    public Proposal createProposal(Proposal proposal) throws SQLException {
        String query = "INSERT INTO Proposal (beginDate, endDate, description, supplierId, title) VALUES (?, ?, ?, ?, ?);";

        proposal.setId(create(query,
            new java.sql.Timestamp(proposal.getBeginDate().getTime()),
            new java.sql.Timestamp(proposal.getEndDate().getTime()),
            proposal.getDescription(),
            proposal.getSupplier().getId(),
            proposal.getTitle()
        ));

        return proposal;
    }

    public ProposalLine createProposalLine(ProposalLine proposalLine) throws SQLException {
        String query = "INSERT INTO ProposalLine (proposalId, productId, price) VALUES (?, ?, ?);";

        proposalLine.setId(create(query,
            proposalLine.getProposal().getId(),
            proposalLine.getProduct().getId(),
            proposalLine.getPrice()
        ));

        return proposalLine;
    }


    // #endregion

    // #region Privates

    private Proposal deserializeProposal(ResultSet resultSet) throws SQLException {
        Proposal proposal = null;

        int currentProposalId = 0;

        // There are more than 1 result, due to the inner join *EEEEEEEEK*
        while (resultSet.next()) {
            // If proposal didn't change (should be only one... but just in case)
            if (resultSet.getInt("id") != currentProposalId) {
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

    private ProposalLine deserializeProposalLine(ResultSet resultSet) throws SQLException {
        // Add all the lines with the products to the proposal.
        ProposalLine line = new ProposalLine();

        if (resultSet.next()) {
            line.setId(resultSet.getInt("id"));
            line.setPrice(resultSet.getFloat("price"));

            line.setProduct(new Product(
                resultSet.getInt("productId"),
                resultSet.getString("productName"),
                resultSet.getString("gtin"),
                new Brand(resultSet.getInt("brandId"), resultSet.getString("brandName")),
                new Category(resultSet.getInt("categoryId"), resultSet.getString("categoryName"))
            ));

            line.setProposal(new Proposal(
                resultSet.getInt("proposalId"),
                resultSet.getTimestamp("beginDate"),
                resultSet.getTimestamp("endDate"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                null,
                new Supplier(
                    resultSet.getInt("supplierId"),
                    resultSet.getString("supplierName"),
                    resultSet.getString("cuit"),
                    resultSet.getString("legalName"),
                    resultSet.getString("role")
                )
            ));
        }

        return line;
    }

    // #endregion
}