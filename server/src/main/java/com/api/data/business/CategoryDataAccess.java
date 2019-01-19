package com.api.data.business;

import com.api.entities.business.Category;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDataAccess extends BaseDataAccess {
    public ArrayList<Category> getCategories() throws SQLException{
        String query = "SELECT * from Category";

        return getMany(rs -> new Category(rs), query);
    }

}
