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

    public Product getProduct(int productId) throws SQLException{
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

    public ArrayList<Product> getProducts(int brandId, int categoryId, int pageIndex, int pageSize, String keyword, String orderBy) throws SQLException{
        String query = "SELECT " +
            "P.*, " +
            "B.name as brandName, " +
            "C.name as categoryName " +
            "FROM " +
            "Product P " +
            "INNER JOIN Brand B ON P.brandId = B.id " +
            "INNER JOIN Category C ON P.categoryId = C.id " +
            "WHERE P.deletedAt IS NULL";

        ArrayList<Object> parameters = new ArrayList<Object>();

        if (brandId != 0) {
            query += " AND B.id = ?";
            parameters.add(brandId);
        }

        if (categoryId != 0) {
            query += " AND C.id = ?";
            parameters.add(categoryId);
        }

        if (keyword != null && !keyword.isEmpty()) {
             query += " AND (P.name LIKE ? or B.name LIKE ? or C.name LIKE ?)";
             parameters.add('%' + keyword + '%');
             parameters.add('%' + keyword + '%');
             parameters.add('%' + keyword + '%');
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

        return getMany(rs -> new Product(rs), query, parameters.toArray());
    }

    public ArrayList<Ranking> mostUsedByProposal(int supplierId) throws SQLException {
        String query = "SELECT " +
            "P.*, " +
            "B.name as brandName, " +
            "C.name as categoryName, " +
            "COUNT(*) as `count` " +
            "FROM " +
            "Product P " +
            "INNER JOIN Brand B ON P.brandId = B.id " +
            "INNER JOIN Category C ON P.categoryId = C.id " +
            "INNER JOIN ProposalLine PL ON P.id = PL.productId " +
            "INNER JOIN Proposal Pr ON Pr.id = PL.proposalId " +
            "WHERE P.deletedAt IS NULL " +
            "AND Pr.supplierId = ? " +
            "GROUP BY P.id " +
            "ORDER BY COUNT(*) DESC;";

        return getMany(rs -> deserializeRanking(rs), query, supplierId);
    }

    private Ranking deserializeRanking(ResultSet rs) throws SQLException {
        return new Ranking(rs.getInt("count"), new Product(rs));
    }

    public Product createProduct(Product product) throws SQLException{
        String query = "INSERT INTO Product (name, gtin, brandId, categoryId) VALUES (?, ?, ?, ?);";

        product.setId(create(query,
            product.getName(),
            product.getGtin(),
            product.getBrand().getId(),
            product.getCategory().getId())
        );

        return product;
    }

    public int updateProduct(Product product) throws SQLException {
        String query = "UPDATE Product SET name = ?, gtin = ?, brandId = ?, categoryId = ? WHERE id = ?;";

        return update(query,
            product.getName(),
            product.getGtin(),
            product.getBrand().getId(),
            product.getCategory().getId(),
            product.getId()
        );
    }

    public boolean validateGtin(String gtin) throws SQLException {
        PreparedStatement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM product WHERE gtin = ?";

        try {
            statement = Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, gtin);

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

    public boolean deleteProduct(Product product) throws SQLException {
        String query = "UPDATE Product SET deletedAt = ? WHERE id = ?;";
        Date now = new Date();

        int rowsEdited = update(query,
            new java.sql.Timestamp(now.getTime()),
            product.getId()
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