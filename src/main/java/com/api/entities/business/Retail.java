package com.api.entities.business;

// #region Imports
import com.api.entities.enums.OrganizationRoles;
import java.sql.ResultSet;
import java.sql.SQLException;

// #endregion

public class Retail extends Organization {

	/**
	* Default empty Retail constructor
	*/
	public Retail() {
		super();
        super.setRole(OrganizationRoles.RETAIL);
	}

    /**
    * SQL Constructor
    */
    public Retail(ResultSet rs) throws SQLException {
        super();
        super.setRole(OrganizationRoles.RETAIL);
    }
}
