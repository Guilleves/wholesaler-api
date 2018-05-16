package com.api.entities.business;

// #region Imports
import com.api.entities.enums.OrganizationRoles;
import com.api.data.business.ProposalDataAccess;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

// #endregion

public class Supplier extends Organization {
	private ArrayList<Proposal> proposals;

	/**
	* Default empty Supplier constructor
	*/
	public Supplier() {
		super();

		proposals = new ArrayList<Proposal>();

        super.setRole(OrganizationRoles.SUPPLIER);
	}

	/**
	* Default empty Supplier constructor
	*/
	public Supplier(Organization organization) {
		super(
			organization.getId(),
			organization.getName(),
			organization.getCuit(),
			organization.getLegalName(),
			organization.getRole()
		);

		proposals = new ArrayList<Proposal>();

        super.setRole(OrganizationRoles.SUPPLIER);
	}

	/**
	* Inheritance Constructor
	*/
	public Supplier(int id, String name, String cuit, String legalName, String role) {
		super(id, name, cuit, legalName, role);
		proposals = new ArrayList<Proposal>();
	}

    /**
    * SQL Constructor
    */
    public Supplier(ResultSet rs) throws SQLException {
        super(rs);
        super.setRole(OrganizationRoles.SUPPLIER);
    }

    public ArrayList<Proposal> getProposals(int organizationId) {
      return proposals;
    }
}
