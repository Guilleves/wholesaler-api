package com.api.logic.business;

// #region Imports

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
      GetOrganizationResponse response = new GetOrganizationResponse();

      // Validate fields.
      ApiException ex = validateSaveOrganization(request);

      if (!ex.isOk())
          throw(ex);

      Organization organization;

      if (request.getRole() == "supplier")
          organization = new Supplier();
      else
          organization = new Retail();

      organization.setId(request.getId());
      organization.setRole(request.getRole());
      organization.setLegalName(request.getLegalName());
      organization.setName(request.getName());

      if (organization.getId() == 0)
          oda.createOrganization(organization);
      else
          oda.updateOrganization(organization);

      return response;
  }

  public GetOrganizationResponse getOrganization(GetOrganizationRequest request) throws ApiException {
    Organization organization = oda.getOrganization(request.getOrganizationId());

    if (organization == null) {
      throw new ApiException("Organization was not found.", Status.NOT_FOUND);
    }

    // Generate the response object.
    GetOrganizationResponse response = new GetOrganizationResponse(
        organization.getId(),
        organization.getName(),
        organization.getLegalName(),
        organization.getCuit()
    );

    return response;
  }

  public ArrayList<GetOrganizationResponse> getOrganizations() throws ApiException {
    ArrayList<GetOrganizationResponse> response = new ArrayList<GetOrganizationResponse>();

    // Fetch organization list.
    ArrayList<Organization> organizations = oda.getOrganizations();

    if (organizations == null || organizations.isEmpty()) {
      throw new ApiException("Couldn't find organizations.", Status.NOT_FOUND);
    }

    // Generate the response object.
    for (Organization organization : organizations) {
      response.add(new GetOrganizationResponse(
        organization.getId(),
        organization.getName(),
        organization.getLegalName(),
        organization.getCuit()
      ));
    }

    return response;
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
