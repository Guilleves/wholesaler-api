package com.api.data.business;

import com.api.data.db.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import com.api.entities.business.Brand;

public class BrandDataAccess extends BaseDataAccess {
    public ArrayList<Brand> getBrands() {
        ArrayList<Brand> brands = new ArrayList<Brand>();
        query = "SELECT * FROM Brand;";

        try {
            statement = Connection.getInstancia().getConn().createStatement();

            resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                brands.add(new Brand(resultSet));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return brands;
    }

    public Brand getBrand(int brandId) {
        Brand brand = null;
        query = "SELECT * FROM Brand WHERE id = ?;";

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
}
