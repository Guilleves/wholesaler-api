package com.api.data.business;

import com.api.entities.business.Category;

import java.sql.SQLException;

import java.util.ArrayList;

// #endregion

public class CategoryDataAccess extends BaseDataAccess {

    // #region ProductSetup

    public ArrayList<Category> getCategories() throws SQLException{
        String query = "SELECT * from Category";

        return getMany(rs -> new Category(rs), query);
    }

}
