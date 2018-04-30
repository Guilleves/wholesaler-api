package com.api.logic.business;

// #region Imports

import java.util.ArrayList;

import com.api.entities.business.Organization;

import com.api.logic.validations.ServerResponse;

import com.api.entities.models.organization.GetOrganizationRequest;
import com.api.entities.models.organization.GetOrganizationResponse;

import com.api.data.business.OrganizationDataAccess;

// #endregion

public class OrganizationLogic {
    private OrganizationDataAccess oda;

    public OrganizationLogic() {
        oda = new OrganizationDataAccess();
    }

    public GetOrganizationResponse getOrganization(GetOrganizationRequest request) throws ServerResponse {
        ServerResponse sr = new ServerResponse();

        Organization organization = oda.getOrganization(request.getOrganizationId());

        if (organization == null) {
            sr.addError("Organization was not found.");
            throw sr;
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

    public ArrayList<GetOrganizationResponse> getOrganizations() throws ServerResponse {
        ServerResponse sr = new ServerResponse();
        ArrayList<GetOrganizationResponse> response = new ArrayList<GetOrganizationResponse>();

        // Fetch organization list.
        ArrayList<Organization> organizations = oda.getOrganizations();

        if (organizations == null || organizations.isEmpty()) {
            sr.addError("Couldn't find organizations.");
            throw sr;
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
}
