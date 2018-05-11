package com.api.entities.business;

// #region Imports
import com.api.data.business.ProposalDataAccess;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

// #endregion

public class Supplier extends Organization {

	/**
	* Default empty Supplier constructor
	*/
	public Supplier() {
		super();
        super.setRole("supplier");
	}

	/**
	* Inheritance Constructor
	*/
	public Supplier(int id, String name, String cuit, String legalName, String role) {
		super(id, name, cuit, legalName, role);
	}

    /**
    * SQL Constructor
    */
    public Supplier(ResultSet rs) throws SQLException {
        super();
        super.setRole("supplier");
    }

    public ArrayList<Proposal> getProposals(int organizationId) {
      ProposalDataAccess pda = new ProposalDataAccess();
      return pda.getProposalsBySupplier(organizationId);
    }
}
