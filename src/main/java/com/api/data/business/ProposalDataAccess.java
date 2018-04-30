package com.api.data.business;

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
            "PL.price as price" +
            "Pr.id as productId " +
            "Pr.name as productName " +
            "Pr.gtin as gtin " +
            "B.id as brandId " +
            "B.name as brandName " +
            "C.id as categoryId " +
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

            // There are more than 1 result, due to the inner join *EEEEEEEEK*
            while (resultSet.next()) {
                proposal = new Proposal();

                int currentProductId = 0;

                proposal.setId(resultSet.getInt("id"));
                proposal.setBeginDate(resultSet.getTimestamp("beginDate"));
                proposal.setEndDate(resultSet.getTimestamp("endDate"));
                proposal.setDescription(resultSet.getString("description"));

                if (resultSet.getInt("productId") != currentProductId) {
                    ProposalLine line = new ProposalLine();

                    line.setProduct(new Product(
                        resultSet.getInt("productId"),
                        resultSet.getString("productName"),
                        resultSet.getString("gtin"),
                        new Brand(resultSet.getInt("brandId"), resultSet.getString("brandName")),
                        new Category(resultSet.getInt("categoryId"), resultSet.getString("categoryName"))
                    ));

                    proposal.getProposalLines().add(line);

                    currentProductId = resultSet.getInt("productId");
                }
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
}
