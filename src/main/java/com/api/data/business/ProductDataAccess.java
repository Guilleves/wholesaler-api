package com.api.data.business;

// #region Imports

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

    public ArrayList<Product> getProducts() throws SQLException{
        String query = "SELECT " +
            "P.*, " +
            "B.name as brandName, " +
            "C.name as categoryName " +
            "FROM " +
            "Product P " +
            "INNER JOIN Brand B ON P.brandId = B.id " +
            "INNER JOIN Category C ON P.categoryId = C.id " +
            "WHERE P.deletedAt IS NULL;";

        return getMany(rs -> new Product(rs), query);
    }

    public ArrayList<Product> getProductsByFilter(int brandId, int categoryId, String keyword) throws SQLException {
        String query = "SELECT p.*, b.name as brandName, c.name as categoryName FROM Product p INNER JOIN Brand b on p.brandId = b.id  INNER JOIN Category c on p.categoryId = c.id WHERE (p.brandId = ? or ? = 0) AND (p.categoryId = ? or ? = 0) AND (p.name LIKE ? or b.name LIKE ? or c.name LIKE ? or ? is null)";

        return getMany(rs -> new Product(rs), query,
            brandId,
            brandId,
            categoryId,
            categoryId,
            '%' + keyword + '%',
            '%' + keyword + '%',
            '%' + keyword + '%',
            keyword
        );
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

    public boolean validateGtin(String gtin) {
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
            e.printStackTrace();
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
