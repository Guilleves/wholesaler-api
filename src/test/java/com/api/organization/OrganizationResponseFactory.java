package com.api.organization;

import com.api.entities.models.organization.GetOrganizationResponse;

public class OrganizationResponseFactory {
    public static GetOrganizationResponse createGetOrganizationResponse(String role) {
        GetOrganizationResponse response = new GetOrganizationResponse(
            1,
            "Test",
            "Test",
            "Test",
            role
        );

        return response;
    }
}
