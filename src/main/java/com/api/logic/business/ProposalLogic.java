package com.api.logic.business;

import com.api.entities.business.Proposal;
import com.api.entities.models.proposal.GetProposalResponse;
import com.api.entities.models.proposal.GetProposalRequest;
import com.api.data.business.ProposalDataAccess;

public class ProposalLogic {
    private ProposalDataAccess pda;

    public ProposalLogic() {
        pda = new ProposalDataAccess();
    }

    public GetProposalResponse getProposal(GetProposalRequest request) {
        Proposal proposal = pda.getProposal(request.getProposalId());

        return new GetProposalResponse(
            proposal.getId(),
            proposal.getDescription(),
            proposal.getBeginDate(),
            proposal.getEndDate()
        );
    }
}
