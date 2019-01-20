package com.api.data.business;

// #region Imports
import com.api.entities.business.Ranking;

import java.sql.ResultSet;
import java.util.Date;
import com.api.entities.business.Category;
import com.api.entities.business.Brand;

import com.api.entities.business.Product;

import com.api.data.db.Connection;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;

// #endregion

public class ProductDataAccess extends BaseDataAccess {

    // #region ProductSetup

    public int countProducts() throws SQLException {
      String query = "SELECT COUNT(*) FROM Product WHERE deletedAt IS NULL";

      return getInt(query);
    }

    public Product getProduct(int productId) throws SQLException {
        String query = "SELECT " +
        "P.*, " +
        "B.name as brandName, " +
        "C.name as categoryName " +
        "FROM " +
        "Product P " +
        "INNER JOIN Brand B ON P.brandId = B.id " +
        "INNER JOIN Category C ON P.brandId = B.id " +
        "WHERE P.id = ? " +
        "AND P.deletedAt IS NULL;";

        return getOne(rs -> new Product(rs), query, productId);
    }

    public ArrayList<Product> getProducts(Integer brandId, Integer categoryId, Integer pageIndex, Integer pageSize, String keyword, String orderBy) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<Object>();

        String productSubQuery = "(SELECT P.* FROM Product P INNER JOIN brand B ON B.id = P.brandId INNER JOIN Category C ON C.id = P.categoryId WHERE P.deletedAt IS NULL ";

        if (brandId != null) {
            productSubQuery += " AND B.id = ?";
            parameters.add(brandId);
        }

        if (categoryId != null) {
            productSubQuery += " AND C.id = ? ";
            parameters.add(categoryId);
        }

        if (keyword != null && !keyword.isEmpty()) {
            productSubQuery += " AND (P.name LIKE ? or B.name LIKE ? or C.name LIKE ?) ";
            parameters.add('%' + keyword + '%');
            parameters.add('%' + keyword + '%');
            parameters.add('%' + keyword + '%');
        }

        if (pageIndex != null && pageSize != null) {
            productSubQuery += " LIMIT ?, ? ";
            parameters.add(pageIndex * pageSize);
            parameters.add(pageSize);
        }

        productSubQuery += ") as P ";

        String query = "SELECT " +
        "P.*, " +
        "B.name as brandName, " +
        "C.name as categoryName " +
        "FROM " +
        productSubQuery +
        "INNER JOIN Brand B ON P.brandId = B.id " +
        "INNER JOIN Category C ON P.categoryId = C.id " +
        "WHERE P.deletedAt IS NULL";


        query += " ORDER BY P.id;";

        return getMany(rs -> new Product(rs), query, parameters.toArray());
    }

    public int countSearch(Integer brandId, Integer categoryId, String keyword) throws SQLException{
        ArrayList<Object> parameters = new ArrayList<Object>();

        String query = "SELECT COUNT(*) as size FROM " +
        "( SELECT P.* FROM Product P " +
        "INNER JOIN Brand B ON P.brandId = B.id " +
        "INNER JOIN Category C ON P.categoryId = C.id " +
        "WHERE P.deletedAt IS NULL";

        if (brandId != null) {
            query += " AND B.id = ?";
            parameters.add(brandId);
        }

        if (categoryId != null) {
            query += " AND C.id = ?";
            parameters.add(categoryId);
        }

        if (keyword != null && !keyword.isEmpty()) {
            query += " AND (P.name LIKE ? or B.name LIKE ? or C.name LIKE ?)";
            parameters.add('%' + keyword + '%');
            parameters.add('%' + keyword + '%');
            parameters.add('%' + keyword + '%');
        }

        query += " GROUP BY P.id ) as x";

        query += ";";

        return getInt(query, parameters.toArray());
    }

    public ArrayList<Ranking> mostUsedByProposal(int supplierId, int amount) throws SQLException {
        ArrayList<Integer> parameters = new ArrayList<Integer>();

        String query = "SELECT " +
        "P.*, " +
        "B.name as brandName, " +
        "C.name as categoryName, " +
        "SUM(OL.quantity) as `count` " +
        "FROM " +
        "Product P " +
        "INNER JOIN Brand B ON P.brandId = B.id " +
        "INNER JOIN Category C ON P.categoryId = C.id " +
        "INNER JOIN ProposalLine PL ON P.id = PL.productId " +
        "INNER JOIN Proposal Pr ON Pr.id = PL.proposalId " +
        "INNER JOIN OrderLine OL ON OL.proposalLineId = PL.id " +
        "WHERE P.deletedAt IS NULL ";
        if (supplierId != 0) {
            query += "AND Pr.supplierId = ? ";
            parameters.add(supplierId);
        }
        query += "GROUP BY P.id " +
        "ORDER BY SUM(OL.quantity) DESC ";

        if (amount != 0) {
            query += "LIMIT ?";
            parameters.add(amount);
        }

        query += ";";

        return getMany(rs -> deserializeRanking(rs), query, parameters.toArray());
    }

    public ArrayList<Ranking> topSellersByOrder(int retailId, int amount) throws SQLException {
        ArrayList<Integer> parameters = new ArrayList<Integer>();

        String query = "SELECT " +
        "P.*, " +
        "B.name as brandName, " +
        "C.name as categoryName, " +
        "SUM(OL.quantity) as `count` " +
        "FROM " +
        "Product P " +
        "INNER JOIN Brand B ON P.brandId = B.id " +
        "INNER JOIN Category C ON P.categoryId = C.id " +
        "INNER JOIN ProposalLine PL ON P.id = PL.productId " +
        "INNER JOIN Proposal Pr ON Pr.id = PL.proposalId " +
        "INNER JOIN OrderLine OL ON OL.proposalLineId = PL.id " +
        "INNER JOIN `Order` O ON OL.orderId = O.id " +
        "WHERE P.deletedAt IS NULL ";

        if (retailId != 0) {
            query += "AND O.retailId = ? ";
            parameters.add(retailId);
        }
        query += "GROUP BY P.id " +
        "ORDER BY SUM(OL.quantity) DESC ";

        if (amount != 0) {
            query += "LIMIT ?";
            parameters.add(amount);
        }

        query += ";";

        return getMany(rs -> deserializeRanking(rs), query, parameters.toArray());
    }

    private Ranking deserializeRanking(ResultSet rs) throws SQLException {
        return new Ranking(rs.getInt("count"), new Product(rs));
    }

    public Product createProduct(Product product) throws SQLException{
        String query = "INSERT INTO Product (name, gtin, description, brandId, categoryId) VALUES (?, ?, ?, ?, ?);";

        product.setId(create(query,
        product.getName(),
        product.getGtin(),
        product.getDescription(),
        product.getBrand().getId(),
        product.getCategory().getId())
        );

        return product;
    }

    public int updateProduct(Product product) throws SQLException {
        String query = "UPDATE Product SET name = ?, gtin = ?, description = ?, brandId = ?, categoryId = ? WHERE id = ?;";

        return update(query,
        product.getName(),
        product.getGtin(),
        product.getDescription(),
        product.getBrand().getId(),
        product.getCategory().getId(),
        product.getId()
        );
    }

    public boolean validateGtin(String gtin, int id) throws SQLException {
        PreparedStatement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM Product WHERE gtin = ? ";

        if (id != 0) {
          query += "AND id != ?";
        }

        try {
            statement = Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, gtin);

            if (id != 0) {
              statement.setInt(2, id);
            }

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        }
        catch(SQLException e) {
            throw e;
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return false;
    }

    public boolean deleteProduct(int productId) throws SQLException {
        String query = "UPDATE Product SET deletedAt = ? WHERE id = ?;";
        Date now = new Date();

        int rowsEdited = update(query,
        new java.sql.Timestamp(now.getTime()),
        productId
        );

        return rowsEdited != 0;
    }

    // #endregion

    // #region BrandInfo

    public Brand getBrand(int brandId) throws SQLException {
        String query = "SELECT * FROM Brand WHERE id = ?";

        return getOne(rs -> new Brand(rs), query, brandId);
    }

    // #endregion

    // #region CategoryInfo

    public Category getCategory(int categoryId) throws SQLException{
        String query = "SELECT * FROM Category WHERE id = ?";

        return getOne(rs -> new Category(rs), query, categoryId);
    }
    // #endregion
}
