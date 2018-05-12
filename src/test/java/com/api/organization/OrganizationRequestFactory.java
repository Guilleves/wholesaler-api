package com.api.organization;

import com.api.entities.enums.OrganizationRoles;
import com.api.entities.models.organization.GetOrganizationsRequest;
import com.api.entities.models.organization.GetOrganizationRequest;

public class OrganizationRequestFactory {
    public static GetOrganizationRequest createGetOrganizationRequest() {
        // GetOrganizationRequest request = new GetOrganizationRequest(1);

        return new GetOrganizationRequest(1);
    }

    public static GetOrganizationsRequest createGetOrganizationsRequest(String role) {
        GetOrganizationsRequest request = null;

        if (role == null)
            request = new GetOrganizationsRequest();

        if (role.equals(OrganizationRoles.SUPPLIER))
            request = new GetOrganizationsRequest(OrganizationRoles.SUPPLIER);

        if (role.equals(OrganizationRoles.RETAIL))
            request = new GetOrganizationsRequest(OrganizationRoles.RETAIL);

        return request;
    }
}
