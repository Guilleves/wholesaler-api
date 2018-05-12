package com.api.data.business;

import com.api.entities.business.Proposal;
import com.api.entities.business.ProposalLine;
import com.api.entities.business.Category;
import com.api.entities.business.Brand;
import com.api.entities.business.Product;
import com.api.entities.business.OrderLine;
import com.api.entities.business.Retail;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.api.data.db.Connection;
import java.sql.PreparedStatement;
import com.api.entities.business.Order;

public class OrderDataAccess extends BaseDataAccess {
    public Order getOrder(int orderId) {
        Order order = null;

        // Eeeeeeeeeeeeeeeeeek...
        query = "SELECT " +
            "O.*, " +
            "P.id as proposalId, " +
            "P.id as proposalTitle, " +
            "P.beginDate, " +
            "P.endDate, " +
            "P.description, " +
            "R.id as organizationId, " +
            "R.name as organizationName, " +
            "R.cuit, " +
            "R.legalName, " +
            "R.role, " +
            "OL.id as orderLineId, " +
            "OL.quantity, " +
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
            "`Order` O " +
            "INNER JOIN Organization R ON R.id = O.retailId " +
            "INNER JOIN OrderLine OL ON OL.orderId = O.id " +
            "INNER JOIN ProposalLine PL ON OL.proposalLineId = PL.id " +
            "INNER JOIN Proposal P ON PL.proposalId = P.id " +
            "INNER JOIN Product Pr ON PL.productId = Pr.id " +
            "INNER JOIN Brand B ON Pr.brandId = B.id " +
            "INNER JOIN Category C ON Pr.categoryId = C.id " +
            "WHERE O.id = ? " +
            "AND PL.deletedAt IS NULL;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, orderId);

            resultSet = ((PreparedStatement)statement).executeQuery();

            order = deserializeOrder(resultSet);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return order;
    }

    private Order deserializeOrder(ResultSet resultSet) throws SQLException {
        Order order = null;

        int currentOrderId = 0;

        // There are more than 1 result, due to the inner join *EEEEEEEEK*
        while (resultSet.next()) {
            // If proposal didn't change (should be only one... but just in case)
            if (resultSet.getInt("id") != currentOrderId) {
                order = new Order();

                order.setId(resultSet.getInt("id"));
                order.setDateOrdered(resultSet.getTimestamp("dateOrdered"));
                order.setRetail(new Retail(
                    resultSet.getInt("organizationId"),
                    resultSet.getString("organizationName"),
                    resultSet.getString("cuit"),
                    resultSet.getString("legalName"),
                    resultSet.getString("role")
                ));

                currentOrderId = order.getId();
            }

            // Add all the lines with the products to the proposal.
            OrderLine line = new OrderLine();

            line.setQuantity(resultSet.getInt("quantity"));
            line.setId(resultSet.getInt("orderLineId"));

            // Add all the lines with the products to the proposal.
            ProposalLine proposalLine = new ProposalLine();

            proposalLine.setPrice(resultSet.getFloat("price"));
            proposalLine.setId(resultSet.getInt("proposalLineId"));

            proposalLine.setProduct(new Product(
                resultSet.getInt("productId"),
                resultSet.getString("productName"),
                resultSet.getString("gtin"),
                new Brand(resultSet.getInt("brandId"), resultSet.getString("brandName")),
                new Category(resultSet.getInt("categoryId"), resultSet.getString("categoryName"))
            ));

            Proposal proposal = new Proposal();

            proposal.setId(resultSet.getInt("proposalId"));
            proposal.setTitle(resultSet.getString("proposalTitle"));
            proposal.setBeginDate(resultSet.getTimestamp("beginDate"));
            proposal.setEndDate(resultSet.getTimestamp("endDate"));
            proposal.setDescription(resultSet.getString("description"));

            proposalLine.setProposal(proposal);

            line.setProposalLine(proposalLine);

            order.getOrderLines().add(line);
        }

        return order;
    }
}
