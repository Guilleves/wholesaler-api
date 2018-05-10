package com.api.entities.business;

// #region Imports
import java.sql.ResultSet;
import java.sql.SQLException;

// #endregion

public class Retail extends Organization {

	/**
	* Default empty Retail constructor
	*/
	public Retail() {
		super();
        super.setRole("retail");
	}

    /**
    * SQL Constructor
    */
    public Retail(ResultSet rs) throws SQLException {
        super();
        super.setRole("retail");
    }
}
