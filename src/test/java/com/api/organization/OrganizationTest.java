package com.api.organization;

import com.api.entities.enums.OrganizationRoles;
import com.api.logic.validations.ApiException;
import com.api.logic.business.OrganizationLogic;
import junit.framework.TestCase;
import com.api.entities.models.organization.GetOrganizationResponse;;

public class OrganizationTest extends TestCase {
    private OrganizationLogic ol;

    protected void setUp() throws Exception {
        super.setUp();
        ol = new OrganizationLogic();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public final void testGetOrganization() {
        try {
            GetOrganizationResponse organization = ol.getOrganization(OrganizationRequestFactory.createGetOrganizationRequest());

            if (organization == null)
                fail("Organization should exist.");

            if (organization.getId() == 0)
                fail("Organization should exist.");

            assertEquals(OrganizationResponseFactory.createGetOrganizationResponse(OrganizationRoles.SUPPLIER), organization);
        }
        catch(ApiException ex) {
            fail("Handeled exception catched: " + ex.getMessage());
        }
    }

    /*public final void testGetOrganizationsWithSupplier() {
        try {
            GetOrganizationsRequest request = OrganizationRequestFactory.createGetOrganizationsRequest(OrganizationRoles.SUPPLIER);

            if (request == null)
                fail("Invalid request."); // Como michetti

            ArrayList<GetOrganizationResponse> organizations = ol.getOrganizations(request);

            if (organizations == null || organizations.isEmpty())
                fail("Organization should exist.");
        }
        catch(ApiException ex) {
            fail("Handeled exception catched: " + ex.getMessage());
        }
    }*/
}
