package com.api.logic.business;

import com.api.rest.security.UserPrincipal;
import com.api.entities.business.Product;
import com.api.data.business.ProductDataAccess;
import com.api.entities.models.proposal.SaveProposalRequest.Line;
import com.api.entities.business.ProposalLine;
import com.api.entities.models.proposal.SaveProposalRequest;
import javax.ws.rs.core.Response.Status;
import com.api.logic.validations.ApiException;
import java.util.ArrayList;
import com.api.entities.business.Proposal;
import com.api.entities.models.proposal.GetProposalResponse;
import com.api.entities.models.proposal.GetProposalRequest;
import com.api.data.business.ProposalDataAccess;

public class ProposalLogic {
    private ProposalDataAccess pda;
    private ProductDataAccess productDa;

    public ProposalLogic() {
        pda = new ProposalDataAccess();
        productDa = new ProductDataAccess();
    }

    public ArrayList<GetProposalResponse> getProposals() throws ApiException {
        ArrayList<Proposal> proposals = pda.getProposals();
        ArrayList<GetProposalResponse> response = new ArrayList<GetProposalResponse>();

        if (proposals == null)
            throw new ApiException("Cound't find any proposal.", Status.NOT_FOUND);

        for(Proposal proposal : proposals) {
            response.add(new GetProposalResponse(
                proposal.getId(),
                proposal.getDescription(),
                proposal.getBeginDate(),
                proposal.getEndDate()
            ));
        }

        return response;
    }

    public GetProposalResponse getProposal(GetProposalRequest request) throws ApiException {
        Proposal proposal = pda.getProposal(request.getProposalId());

        if (proposal == null)
            throw new ApiException("Proposal not found.", Status.NOT_FOUND);

        return new GetProposalResponse(
            proposal.getId(),
            proposal.getDescription(),
            proposal.getBeginDate(),
            proposal.getEndDate()
        );
    }

    public GetProposalResponse saveProposal(SaveProposalRequest request, UserPrincipal loggedUser) throws ApiException {
        Proposal proposal = new Proposal();

        // Only suppliers can create Proposals
        if(!(loggedUser.getRole().equals("supplier")))
            throw new ApiException("You don't have permissions to access here.", Status.UNAUTHORIZED);

        // Validate fields.
        ApiException ex = validateSaveProposal(request);

        if (!ex.isOk())
            throw ex;

        ArrayList<ProposalLine> lines = new ArrayList<ProposalLine>();

        // For every line, get the product and validate its existence. Then add it to the proposal.
        for(Line line : request.getLines()) {
            Product product = productDa.getProduct(line.getProductId());

            if (product == null)
                throw new ApiException("One of the selected prodcuts coudn't be found.", Status.NOT_FOUND);

            ProposalLine proposalLine = new ProposalLine();

            proposalLine.setProduct(product);
            proposalLine.setPrice(line.getPrice());

            // Are we supporting this? Proposal, with lines with proposal with lines with... etc. Don't think so.
            //proposalLine.setProposal(proposal);

            lines.add(proposalLine);
        }

        // Set primitive data.
        // TODO: Supplier missing until @guilleves finishes with the setup.
        proposal.setDescription(request.getDescription());
        proposal.setBeginDate(request.getBeginDate());
        proposal.setEndDate(request.getEndDate());

        Proposal createdProposal = pda.registerProposal(proposal);

        return new GetProposalResponse(
            createdProposal.getId(),
            createdProposal.getDescription(),
            createdProposal.getBeginDate(),
            createdProposal.getEndDate()
        );
    }

    private ApiException validateSaveProposal(SaveProposalRequest request) {
        ApiException ex = new ApiException();

        if (request.getBeginDate() == null)
            ex.addError("Begin date cannot be empty.");

        if (request.getEndDate() == null)
            ex.addError("End date cannot be empty.");

        if (request.getBeginDate().after(request.getEndDate()))
            ex.addError("Begin date cannot be greater than end date.");

        return ex;
    }
}
