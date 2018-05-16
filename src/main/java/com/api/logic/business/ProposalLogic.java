package com.api.logic.business;

import com.api.entities.business.Supplier;
import com.api.entities.business.User;
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

    public ArrayList<GetProposalResponse> getProposals(String status, Integer supplierId) throws ApiException {
        ArrayList<Proposal> proposals = pda.getProposals(status, supplierId);
        ArrayList<GetProposalResponse> response = new ArrayList<GetProposalResponse>();

        if (proposals == null || proposals.isEmpty())
            throw new ApiException("Cound't find any proposal.", Status.NOT_FOUND);

        for(Proposal proposal : proposals) {
            response.add(new GetProposalResponse(
                proposal.getId(),
                proposal.getTitle(),
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
            proposal.getTitle(),
            proposal.getDescription(),
            proposal.getBeginDate(),
            proposal.getEndDate()
        );
    }

    public GetProposalResponse saveProposal(SaveProposalRequest request, User loggedUser) throws ApiException {
        Proposal proposal = new Proposal();

        // Only suppliers can create Proposals
        if(!(loggedUser.getOrganization().getRole().equals("supplier")))
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
        proposal.setSupplier(new Supplier(loggedUser.getOrganization()));
        proposal.setTitle(request.getTitle());
        proposal.setDescription(request.getDescription());
        proposal.setBeginDate(request.getBeginDate());
        proposal.setEndDate(request.getEndDate());
        proposal.setProposalLines(lines);

        Proposal createdProposal = pda.registerProposal(proposal);

        return new GetProposalResponse(
            createdProposal.getId(),
            createdProposal.getTitle(),
            createdProposal.getDescription(),
            createdProposal.getBeginDate(),
            createdProposal.getEndDate()
        );
    }

    private ApiException validateSaveProposal(SaveProposalRequest request) {
        ApiException ex = new ApiException();

        if (request.getTitle() == null)
            ex.addError("Title can't be empty.");

        if (request.getBeginDate() == null)
            ex.addError("Begin date cannot be empty.");

        if (request.getEndDate() == null)
            ex.addError("End date cannot be empty.");

        if (request.getBeginDate().after(request.getEndDate()))
            ex.addError("Begin date cannot be greater than end date.");

        return ex;
    }
}
