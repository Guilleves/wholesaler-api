package com.api.logic.business;

import java.sql.SQLException;
import com.api.entities.models.BaseSearchResponse;
import com.api.entities.models.proposal.GetProposalsRequest;
import com.api.entities.models.organization.GetOrganizationResponse;
import com.api.entities.models.product.GetProductResponse;
import com.api.entities.models.proposal.GetProposalsResponse;
import com.api.entities.models.proposal.DeleteProposalResponse;
import com.api.entities.enums.OrganizationRoles;
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
import com.api.data.business.ProposalDataAccess;

public class ProposalLogic {
  private ProposalDataAccess pda;
  private ProductDataAccess productDa;

  public ProposalLogic() {
    pda = new ProposalDataAccess();
    productDa = new ProductDataAccess();
  }

  public int countProposals(User loggedUser) throws ApiException {
    try {
      if(loggedUser.getOrganization().getRole().equals(OrganizationRoles.SUPPLIER)) {
        return pda.countProposals(loggedUser.getOrganization().getId());
      } else {
        throw new ApiException("Unauthorized.", Status.UNAUTHORIZED);
      }
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public BaseSearchResponse getProposals(GetProposalsRequest request, User loggedUser) throws ApiException {
    try {
      if(loggedUser.getOrganization().getRole().equals(OrganizationRoles.SUPPLIER)) {
        // When the user is a supplier, only show own proposals.
        request.setSupplierId(loggedUser.getOrganization().getId());
      }

      ArrayList<Proposal> proposals = pda.getProposals(request.getStatus(), request.getSupplierId(), request.isShowDeleted(), request.getOrderBy(), request.getPageSize(), request.getPageIndex());

      ArrayList<GetProposalsResponse> response = new ArrayList<GetProposalsResponse>();

      if (proposals == null || proposals.isEmpty())
      throw new ApiException("Cound't find any proposal.", Status.NOT_FOUND);

      for(Proposal proposal : proposals) {
        response.add(new GetProposalsResponse(
        proposal.getId(),
        proposal.getTitle(),
        proposal.getDescription(),
        proposal.getStatus(),
        proposal.getBeginDate(),
        proposal.getEndDate()
        ));
      }

      return new BaseSearchResponse(pda.countSearch(request.getStatus(), request.getSupplierId()), response);
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public GetProposalResponse getProposal(int proposalId) throws ApiException {
    try {
      Proposal proposal = pda.getProposal(proposalId);

      if (proposal == null)
      throw new ApiException("Proposal not found.", Status.NOT_FOUND);

      ArrayList<GetProposalResponse.Line> responseLines = new ArrayList<GetProposalResponse.Line>();

      for(ProposalLine line : proposal.getProposalLines()) {
        responseLines.add(new GetProposalResponse.Line(
        line.getId(),
        new GetProductResponse (
        line.getProduct().getId(),
        line.getProduct().getName(),
        line.getProduct().getGtin(),
        line.getProduct().getDescription(),
        line.getProduct().getBrand().getId(),
        line.getProduct().getBrand().getName(),
        line.getProduct().getCategory().getId(),
        line.getProduct().getCategory().getName()
        ),
        line.getPrice()
        ));
      }

      return new GetProposalResponse(
      proposal.getId(),
      proposal.getTitle(),
      proposal.getDescription(),
      proposal.getStatus(),
      proposal.getBeginDate(),
      proposal.getEndDate(),
      responseLines,
      new GetOrganizationResponse(
      proposal.getSupplier().getId(),
      proposal.getSupplier().getName(),
      proposal.getSupplier().getLegalName(),
      proposal.getSupplier().getCuit(),
      proposal.getSupplier().getRole()
      )
      );
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public DeleteProposalResponse deleteProposal(int proposalId, User loggedUser) throws ApiException {
    try {
      // Only suppliers can delete Proposals
      if(!(loggedUser.getOrganization().getRole().equals(OrganizationRoles.SUPPLIER)))
      throw new ApiException("You don't have permissions to access here.", Status.UNAUTHORIZED);

      return new DeleteProposalResponse(pda.deleteProposal(proposalId));
    }
    catch (SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public GetProposalResponse saveProposal(SaveProposalRequest request, User loggedUser) throws ApiException {
    try {
      Proposal proposal = new Proposal();

      // Only suppliers can create Proposals
      if(!(loggedUser.getOrganization().getRole().equals(OrganizationRoles.SUPPLIER)))
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

      ArrayList<GetProposalResponse.Line> responseLines = new ArrayList<GetProposalResponse.Line>();

      for(ProposalLine line : createdProposal.getProposalLines()) {
        responseLines.add(new GetProposalResponse.Line(
        line.getId(),
        new GetProductResponse (
        line.getProduct().getId(),
        line.getProduct().getName(),
        line.getProduct().getGtin(),
        line.getProduct().getDescription(),
        line.getProduct().getBrand().getId(),
        line.getProduct().getBrand().getName(),
        line.getProduct().getCategory().getId(),
        line.getProduct().getCategory().getName()
        ),
        line.getPrice()
        ));
      }

      return new GetProposalResponse(
      createdProposal.getId(),
      createdProposal.getTitle(),
      createdProposal.getDescription(),
      createdProposal.getStatus(),
      createdProposal.getBeginDate(),
      createdProposal.getEndDate(),
      responseLines,
      new GetOrganizationResponse(
      createdProposal.getSupplier().getId(),
      createdProposal.getSupplier().getName(),
      createdProposal.getSupplier().getLegalName(),
      createdProposal.getSupplier().getCuit(),
      createdProposal.getSupplier().getRole()
      )
      );
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  private ApiException validateSaveProposal(SaveProposalRequest request) {
    ApiException ex = new ApiException();

    if (request.getTitle() == null || request.getTitle().isEmpty())
    ex.addError("Title can't be empty.");

    if (request.getBeginDate() == null)
    ex.addError("Begin date cannot be empty.");

    if (request.getEndDate() == null)
    ex.addError("End date cannot be empty.");

    if (request.getBeginDate() != null && request.getEndDate() != null && request.getBeginDate().after(request.getEndDate()))
    ex.addError("Begin date cannot be greater than end date.");

    if (request.getLines() == null || request.getLines().isEmpty())
    ex.addError("At least a proposal line is needed.");

    for (Line line : request.getLines()) {
      if (line.getPrice() <= 0f) {
        ex.addError("Price cannot be negative");
        break;
      }
    }

    return ex;
  }
}
