package com.api.data.business;

import java.util.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.ArrayList;
import com.api.entities.business.Proposal;
import com.api.entities.business.ProposalLine;
import com.api.entities.business.Category;
import com.api.entities.business.Brand;
import com.api.entities.business.Product;
import com.api.entities.business.OrderLine;
import com.api.entities.business.Retail;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.api.data.db.Connection;
import com.api.entities.business.Order;

public class OrderDataAccess extends BaseDataAccess {
    public Order getOrder(int orderId) throws SQLException {
        // Eeeeeeeeeeeeeeeeeek...
        String query = "SELECT " +
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
            "Pr.description as productDescription, " +
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

        return this.getOneWithoutStatement(rs -> deserializeOrder(rs), query, orderId);
    }

    public ArrayList<Order> getOrders(Date fromDate, Date toDate, int retailId, String orderBy, int pageSize, int pageIndex) throws SQLException{
        // Eeeeeeeeeeeeeeeeeek...
        String query = "SELECT " +
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
            "Pr.description as productDescription, " +
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
            "WHERE PL.deletedAt IS NULL";

            ArrayList<Object> parameters = new ArrayList<Object>();

            if (fromDate != null) {
                query += " AND O.dateOrdered > ?";
                parameters.add(fromDate);
            }

            if (toDate != null) {
                query += " AND O.dateOrdered < ?";
                parameters.add(toDate);
            }

            if (retailId != 0) {
                query += " AND O.retailId = ?";
                parameters.add(retailId);
            }

            // this is not working...
            /*if (orderBy != null && !orderBy.isEmpty()) {
                query += " ORDER BY ?";
                parameters.add(orderBy);
            }*/

            if (pageIndex != 0 && pageSize != 0) {
                query += " LIMIT ?, ?";
                parameters.add(pageIndex - 1);
                parameters.add(pageSize);
            }

            query += ";";

        return getManyWithoutStatement(rs -> deserializeOrders(rs), query, parameters.toArray());
    }

    public Order registerOrder(Order order) throws SQLException{
        java.sql.Connection conn = Connection.getInstancia().getConn();

        try {
            conn.setAutoCommit(false);

            // All this messy code is to run everything as a transaction.
            order = createOrder(order);

            for (OrderLine ol : order.getOrderLines()) {
                // TODO: IDK other way to avoid infinite loops, at least this way it works.
                Order o = new Order();
                o.setId(order.getId());
                ol.setOrder(o);

                // Would this do the trick? Damn java, you tha boss.
                ol = createOrderLine(ol);
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

        return order;
    }

    public Order createOrder(Order order) throws SQLException{
        String query = "INSERT INTO `Order` (dateOrdered, retailId) VALUES (?, ?);";

        order.setId(this.create(query,
            new Timestamp(order.getDateOrdered().getTime()),
            order.getRetail().getId())
        );

        return order;
    }

    public OrderLine createOrderLine(OrderLine orderLine) throws SQLException {
        String query = "INSERT INTO OrderLine (proposalLineId, orderId, quantity) VALUES (?, ?, ?);";

        orderLine.setId(this.create(query,
            orderLine.getProposalLine().getId(),
            orderLine.getOrder().getId(),
            orderLine.getQuantity())
        );

        return orderLine;
    }

    private ArrayList<Order> deserializeOrders(ResultSet resultSet) throws SQLException {
        Order order = null;
        HashMap<Integer, Order> orders = new HashMap<Integer, Order>();

        // There are more than 1 result, due to the inner join
        while (resultSet.next()) {
            order = orders.get(resultSet.getInt("id"));

            // When order changes, add it to the arrayList and start filling it.
            if (order == null) {
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

                orders.put(order.getId(), order);
            }

            // Add all the lines with the proposals and product.
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
                resultSet.getString("description"),
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

        return new ArrayList<Order>(orders.values());
    }

    private Order deserializeOrder(ResultSet resultSet) throws SQLException {
        Order order = null;

        int currentOrderId = 0;

        // There are more than 1 result, due to the inner join
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

            // Add all the lines with the proposals and product.
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
                resultSet.getString("description"),
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
