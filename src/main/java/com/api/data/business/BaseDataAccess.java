package com.api.data.business;

// #region Imports
import java.sql.ResultSet;
import java.sql.Statement;
// #endregion

public class BaseDataAccess {
    protected Statement statement = null;
    protected ResultSet resultSet = null;
    protected String query = null;
}
