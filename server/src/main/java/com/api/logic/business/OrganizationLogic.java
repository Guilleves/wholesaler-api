package com.api.logic.business;

// #region Imports
import com.api.entities.business.Ranking;
import com.api.entities.business.User;
import com.api.entities.models.organization.GetRankingResponse;

import com.api.entities.models.organization.GetSuppliersResponse;
import java.sql.SQLException;
import com.api.entities.models.organization.GetOrganizationsRequest;
import com.api.entities.enums.OrganizationRoles;
import com.api.entities.business.Supplier;
import com.api.entities.business.Retail;
import com.api.entities.models.organization.SaveOrganizationRequest;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;

import com.api.entities.business.Organization;

import com.api.logic.validations.ApiException;

import com.api.entities.models.organization.GetOrganizationRequest;
import com.api.entities.models.organization.GetOrganizationResponse;

import com.api.data.business.OrganizationDataAccess;

// #endregion

public class OrganizationLogic {
  private OrganizationDataAccess oda;

  public OrganizationLogic() {
    oda = new OrganizationDataAccess();
  }

  public GetOrganizationResponse saveOrganization(SaveOrganizationRequest request) throws ApiException {
    try {
      GetOrganizationResponse response = new GetOrganizationResponse();

      // Validate fields.
      ApiException ex = validateSaveOrganization(request);

      if (!ex.isOk())
      throw(ex);

      Organization organization;

      if (request.getRole().equals(OrganizationRoles.SUPPLIER))
      organization = new Supplier();
      else
      organization = new Retail();

      organization.setId(request.getId());
      organization.setRole(request.getRole());
      organization.setCuit(request.getCuit());
      organization.setLegalName(request.getLegalName());
      organization.setName(request.getName());

      if (organization.getId() == 0)
      organization = oda.createOrganization(organization);
      else
      oda.updateOrganization(organization);

      return response;
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public ArrayList<GetSuppliersResponse> getSuppliers() throws ApiException {
    try {
      ArrayList<GetSuppliersResponse> response = new ArrayList<GetSuppliersResponse>();

      // Fetch product list.
      ArrayList<Organization> suppliers = oda.getOrganizations(OrganizationRoles.SUPPLIER);

      if (suppliers == null || suppliers.isEmpty())
      throw new ApiException("Couldn't find any supplier.", Status.NOT_FOUND);

      // Generate the response object.
      for (Organization supplier : suppliers) {
        response.add(new GetSuppliersResponse(
        supplier.getId(),
        supplier.getName()
        ));
      }

      return response;
    }
    catch(SQLException e) {
      throw new ApiException(e);
    }
  }

  public ArrayList<GetSuppliersResponse> getRetails() throws ApiException {
    try {
      ArrayList<GetSuppliersResponse> response = new ArrayList<GetSuppliersResponse>();

      // Fetch product list.
      ArrayList<Organization> suppliers = oda.getOrganizations(OrganizationRoles.RETAIL);

      if (suppliers == null || suppliers.isEmpty())
      throw new ApiException("Couldn't find any retail.", Status.NOT_FOUND);

      // Generate the response object.
      for (Organization supplier : suppliers) {
        response.add(new GetSuppliersResponse(
        supplier.getId(),
        supplier.getName()
        ));
      }

      return response;
    }
    catch(SQLException e) {
      throw new ApiException(e);
    }
  }

  public ArrayList<GetRankingResponse> getProfitsByOrganization(User loggedUser, int amount) throws ApiException {
    try {
      ArrayList<GetRankingResponse> response = new ArrayList<GetRankingResponse>();
      ArrayList<Ranking> organizations = null;

      if (loggedUser.getOrganization().getRole().equals(OrganizationRoles.SUPPLIER))
      organizations = oda.getProfitsByRetail(loggedUser.getOrganization().getId(), amount);
      else
      organizations = oda.getSuppliersTopSellers(loggedUser.getOrganization().getId(), amount);

      if (organizations == null || organizations.isEmpty())
      throw new ApiException("Not offers have been made.", Status.NOT_FOUND);

      // Generate the response object.
      for (Ranking organization : organizations) {
        response.add(new GetRankingResponse(
        new GetOrganizationResponse(
        ((Organization)organization.getEntity()).getId(),
        ((Organization)organization.getEntity()).getName(),
        ((Organization)organization.getEntity()).getLegalName(),
        ((Organization)organization.getEntity()).getCuit(),
        ((Organization)organization.getEntity()).getRole()),
        organization.getSum()
        ));
      }

      return response;
    }
    catch(SQLException e) {
      e.printStackTrace();
      throw new ApiException(e);
    }
  }

  public GetOrganizationResponse getOrganization(GetOrganizationRequest request) throws ApiException {
    try {
      Organization organization = oda.getOrganization(request.getOrganizationId());

      if (organization == null) {
        throw new ApiException("Organization was not found.", Status.NOT_FOUND);
      }

      // Generate the response object.
      GetOrganizationResponse response = new GetOrganizationResponse(
      organization.getId(),
      organization.getName(),
      organization.getLegalName(),
      organization.getCuit(),
      organization.getRole()
      );

      return response;
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public ArrayList<GetOrganizationResponse> getOrganizations(GetOrganizationsRequest request) throws ApiException {
    try {
      ArrayList<GetOrganizationResponse> response = new ArrayList<GetOrganizationResponse>();

      // Fetch organization list.
      ArrayList<Organization> organizations;

      if (request.getRole() == null || request.getRole().isEmpty())
      organizations = oda.getOrganizations();
      else
      organizations = oda.getOrganizations(request.getRole());

      if (organizations == null || organizations.isEmpty()) {
        throw new ApiException("Couldn't find organizations.", Status.NOT_FOUND);
      }

      // Generate the response object.
      for (Organization organization : organizations) {
        response.add(new GetOrganizationResponse(
        organization.getId(),
        organization.getName(),
        organization.getLegalName(),
        organization.getCuit(),
        organization.getRole()
        ));
      }

      return response;
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  private ApiException validateSaveOrganization(SaveOrganizationRequest request) {
    ApiException ex = new ApiException();

    if (request.getLegalName() == null || request.getLegalName().isEmpty())
    ex.addError("Legal name is a mandatory field.");

    if (request.getCuit() == null || request.getCuit().isEmpty())
    ex.addError("Cuit is a mandatory field");

    return ex;
  }
}
