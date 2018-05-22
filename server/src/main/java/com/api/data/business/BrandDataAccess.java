package com.api.data.business;

import com.api.entities.business.Brand;

import java.sql.SQLException;

import java.util.ArrayList;

// #endregion

public class BrandDataAccess extends BaseDataAccess {

    // #region ProductSetup

    public ArrayList<Brand> getBrands() throws SQLException{
        String query = "SELECT * from Brand";

        return getMany(rs -> new Brand(rs), query);
    }

}
