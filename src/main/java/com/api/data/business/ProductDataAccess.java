package com.api.data.business;

// #region Import
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

    public Product getProduct(int productId) {
        Product product = null;

        // Eeeeeeeeeeeeeeeeeek...
        query = "SELECT " +
            "P.*, " +
            "B.name as brandName, " +
            "C.name as categoryName " +
            "FROM " +
            "Product P " +
            "INNER JOIN Brand B ON P.brandId = B.id " +
            "INNER JOIN Category C ON P.brandId = B.id " +
            "WHERE P.id = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, productId);

            resultSet = ((PreparedStatement)statement).executeQuery();

            if (resultSet.next()) {
                product = new Product(resultSet);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return product;
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<Product>();

        query = "SELECT " +
            "P.*, " +
            "B.name as brandName, " +
            "C.name as categoryName " +
            "FROM " +
            "Product P " +
            "INNER JOIN Brand B ON P.brandId = B.id " +
            "INNER JOIN Category C ON P.brandId = B.id ";

        try {
            statement = Connection.getInstancia().getConn().createStatement();

            resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                products.add(new Product(resultSet));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return products;
    }

    public int createProduct(Product product) {
        int id = 0;

        query = "INSERT INTO Product (name, gtin, brandId, categoryId) VALUES (?, ?, ?, ?);";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, product.getName());
            ((PreparedStatement)statement).setString(2, product.getGtin());
            ((PreparedStatement)statement).setInt(3, product.getBrand().getId());
            ((PreparedStatement)statement).setInt(4, product.getCategory().getId());

            ((PreparedStatement)statement).executeUpdate();

            resultSet = statement.getGeneratedKeys();

            // If it created the user, return the created id
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return id;
    }

    public int updateProduct(Product product) {
        int id = 0;

        query = "UPDATE Product SET name = ?, gtin = ?, brandId = ?, categoryId = ? WHERE id = ?;";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, product.getName());
            ((PreparedStatement)statement).setString(2, product.getGtin());
            ((PreparedStatement)statement).setInt(3, product.getBrand().getId());
            ((PreparedStatement)statement).setInt(4, product.getCategory().getId());
            ((PreparedStatement)statement).setInt(5, product.getId());

            ((PreparedStatement)statement).executeUpdate();

            resultSet = statement.getGeneratedKeys();

            // If it updated the user, return the updated id
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return id;
    }

    public boolean validateGtin(String gtin) {
        query = "SELECT * FROM product WHERE gtin = ?";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setString(1, gtin);

            resultSet = ((PreparedStatement)statement).executeQuery();

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

    // #endregion

    // #region BrandInfo

    public Brand getBrand(int brandId) {
        Brand brand = null;

        query = "SELECT * FROM Brand WHERE id = ?";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, brandId);

            resultSet = ((PreparedStatement)statement).executeQuery();

            if (resultSet.next()) {
                brand = new Brand(resultSet);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return brand;
    }

    // #endregion

    // #region CategoryInfo

    public Category getCategory(int categoryId) {
        Category category = null;

        query = "SELECT * FROM Category WHERE id = ?";

        try {
            statement = (PreparedStatement)Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ((PreparedStatement)statement).setInt(1, categoryId);

            resultSet = ((PreparedStatement)statement).executeQuery();

            if (resultSet.next()) {
                category = new Category(resultSet);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return category;
    }

    // #endregion
}
