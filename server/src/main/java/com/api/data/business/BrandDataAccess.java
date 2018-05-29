package com.api.data.business;

import com.api.entities.business.Brand;

import java.sql.SQLException;

import java.util.ArrayList;

// #endregion

public class BrandDataAccess extends BaseDataAccess {

    // #region ProductSetup

    public Brand getBrand(int brandId) throws SQLException{
        String query = "SELECT * FROM Brand WHERE id = ?";

        return getOne(rs -> new Brand(rs), query, brandId);
    }

    public ArrayList<Brand> getBrands() throws SQLException{
        String query = "SELECT * from Brand";

        return getMany(rs -> new Brand(rs), query);
    }

    public int countBrands() throws SQLException{
        String query = "SELECT COUNT(*) FROM Brand";

        return getInt(query);
    }

    public ArrayList<Brand> getBrands(Integer pageSize, Integer pageIndex) throws SQLException{
        String query = "SELECT * from Brand LIMIT ?, ?";

        return getMany(rs -> new Brand(rs), query, pageSize * pageIndex, pageSize);
    }

}
