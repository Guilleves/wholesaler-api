package com.api.logic.business;

import com.api.entities.business.Ranking;
import com.api.entities.models.order.GetRankingResponse;
import com.api.entities.business.Proposal;
import com.api.entities.models.BaseSearchResponse;
import com.api.entities.models.order.GetOrdersResponse;
import com.api.entities.models.order.GetOrdersRequest;
import java.sql.SQLException;
import com.api.data.business.ProposalDataAccess;
import com.api.entities.models.order.SaveOrderRequest.Line;
import com.api.entities.enums.OrganizationRoles;
import com.api.entities.business.User;
import com.api.entities.business.Retail;
import com.api.entities.models.order.SaveOrderRequest;
import java.util.ArrayList;
import com.api.entities.models.organization.GetOrganizationResponse;
import com.api.entities.models.product.GetProductResponse;
import com.api.entities.business.OrderLine;
import javax.ws.rs.core.Response.Status;
import com.api.logic.validations.ApiException;
import com.api.entities.business.Order;
import com.api.entities.models.order.GetOrderResponse;
import com.api.entities.models.order.GetOrderRequest;
import com.api.data.business.OrderDataAccess;

public class OrderLogic {
  private ProposalDataAccess pda;
  private OrderDataAccess oda;

  public OrderLogic() {
    pda = new ProposalDataAccess();
    oda = new OrderDataAccess();
  }

  public int countOrders(User loggedUser) throws ApiException {
    try {
      if (loggedUser.getOrganization().getRole().equals(OrganizationRoles.RETAIL))
        return oda.countOrdersForRetailers(loggedUser.getOrganization().getId());
      else
        return oda.countOrdersForSuppliers(loggedUser.getOrganization().getId());
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public BaseSearchResponse getOrders(GetOrdersRequest request, User loggedUser) throws ApiException {
    try {
      ArrayList<Order> orders = null;

      if (loggedUser.getOrganization().getRole().equals(OrganizationRoles.RETAIL)) {
        // When the user is a retailer, only show own orders.
        request.setRetailId(loggedUser.getOrganization().getId());
      } else {
        request.setSupplierId(loggedUser.getOrganization().getId());
      }

      orders = oda.getOrders(
      request.getFromDate(),
      request.getToDate(),
      request.getRetailId(),
      request.getProposalId(),
      request.getOrderBy(),
      request.getPageSize(),
      request.getPageIndex(),
      request.getSupplierId(),
      request.isShowDeleted()
      );

      if (orders == null || orders.isEmpty())
      throw new ApiException("Cound't find any order.", Status.NOT_FOUND);

      ArrayList<GetOrdersResponse> response = new ArrayList<GetOrdersResponse>();

      for (Order order : orders) {
        response.add(new GetOrdersResponse(
        order.getId(),
        order.getDateOrdered(),
        new GetOrganizationResponse(
        order.getRetail().getId(),
        order.getRetail().getName(),
        order.getRetail().getLegalName(),
        order.getRetail().getCuit(),
        order.getRetail().getRole()
        )
        ));
      }

      return new BaseSearchResponse(
      oda.countSearch(
      request.getFromDate(),
      request.getToDate(),
      request.getRetailId(),
      request.getSupplierId(),
      request.isShowDeleted()),
      response
      );
    }
    catch(SQLException e) {
      throw new ApiException(e);
    }
  }

  public GetOrderResponse getOrder(GetOrderRequest request) throws ApiException {
    Order order = null;

    try {
      order = oda.getOrder(request.getOrderId());
    }
    catch(SQLException e) {
      throw new ApiException(e);
    }

    if (order == null)
    throw new ApiException("Cound't find any order.", Status.NOT_FOUND);


    return new GetOrderResponse(
    order.getId(),
    order.getDateOrdered(),
    getOrderLine(order.getOrderLines()),
    new GetOrganizationResponse(
    order.getRetail().getId(),
    order.getRetail().getName(),
    order.getRetail().getLegalName(),
    order.getRetail().getCuit(),
    order.getRetail().getRole()
    )
    );
  }

  public ArrayList<GetRankingResponse> getOrderCountByMonth(User loggedUser) throws ApiException {
    ArrayList<Ranking> orders = null;
    ArrayList<GetRankingResponse> response = new ArrayList<GetRankingResponse>();

    try {
      if (loggedUser.getOrganization().getRole().equals(OrganizationRoles.SUPPLIER))
        orders = oda.getOrderCountByMonth(loggedUser.getOrganization().getId());
      else
        orders = oda.getOwnOrderCountByMonth(loggedUser.getOrganization().getId());

      if (orders == null || orders.isEmpty())
      throw new ApiException("Not orders have been made.", Status.NOT_FOUND);

      // Generate the response object.
      for (Ranking order : orders) {
        response.add(new GetRankingResponse(
          order.getDate(),
          order.getCount()
        ));
      }

      return response;
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  public GetOrderResponse createOrder(SaveOrderRequest request, User loggedUser) throws ApiException {
    try {
      Order order = new Order();

      // Only suppliers can create Proposals
      if(!(loggedUser.getOrganization().getRole().equals(OrganizationRoles.RETAIL)))
      throw new ApiException("You don't have permissions to access here.", Status.UNAUTHORIZED);

      // Validate fields.
      ApiException ex = validateSaveOrder(request);

      if (!ex.isOk())
      throw ex;

      ArrayList<OrderLine> lines = new ArrayList<OrderLine>();

      // For every line, get the product and validate its existence. Then add it to the proposal.
      for(Line line : request.getLines()) {
        OrderLine orderLine = new OrderLine();

        orderLine.setProposalLine(pda.getProposalLine(line.getProposalLineId()));

        if (orderLine.getProposalLine() == null)
        throw new ApiException("At least one proposal line could not be found", Status.NOT_FOUND);

        if (line.getQuantity() == 0)
        throw new ApiException("Amount cannot be empty or 0", Status.BAD_REQUEST);

        orderLine.setQuantity(line.getQuantity());

        lines.add(orderLine);
      }

      Proposal selectedProposal = lines.get(0).getProposalLine().getProposal();

      if (!selectedProposal.isActive())
      throw new ApiException("Proposal must be active");

      // Set primitive data.
      order.setDateOrdered(request.getDateOrdered());
      order.setOrderLines(lines);
      order.setRetail((Retail)loggedUser.getOrganization());

      order = oda.registerOrder(order);

      return new GetOrderResponse(
      order.getId(),
      order.getDateOrdered(),
      getOrderLine(order.getOrderLines()),
      new GetOrganizationResponse(
      order.getRetail().getId(),
      order.getRetail().getName(),
      order.getRetail().getLegalName(),
      order.getRetail().getCuit(),
      order.getRetail().getRole()
      )
      );
    }
    catch(SQLException ex) {
      throw new ApiException(ex);
    }
  }

  private ApiException validateSaveOrder(SaveOrderRequest request) {
    ApiException ex = new ApiException();

    if (request.getDateOrdered() == null)
    ex.addError("The field ordered date is required.");

    if (request.getLines() == null || request.getLines().isEmpty())
    ex.addError("At least a line order is needed.");

    for(Line line: request.getLines()) {
      if (line.getQuantity() <= 0) {
        ex.addError("Quantity cannot be negative.");
        break;
      }
    }

    return ex;
  }

  private ArrayList<GetOrderResponse.OrderLine> getOrderLine(ArrayList<OrderLine> orderLines) {
    ArrayList<GetOrderResponse.OrderLine> lineResponse = new ArrayList<GetOrderResponse.OrderLine>();

    for(OrderLine orderLine : orderLines) {
      lineResponse.add(new GetOrderResponse.OrderLine(
      orderLine.getId(),
      orderLine.getQuantity(),
      new GetOrderResponse.OrderLine.ProposalLine(
      orderLine.getProposalLine().getId(),
      new GetOrderResponse.OrderLine.ProposalLine.Proposal(
      orderLine.getProposalLine().getProposal().getId(),
      orderLine.getProposalLine().getProposal().getBeginDate(),
      orderLine.getProposalLine().getProposal().getEndDate(),
      orderLine.getProposalLine().getProposal().getTitle(),
      orderLine.getProposalLine().getProposal().getDescription()
      ),
      new GetProductResponse(
      orderLine.getProposalLine().getProduct().getId(),
      orderLine.getProposalLine().getProduct().getName(),
      orderLine.getProposalLine().getProduct().getGtin(),
      orderLine.getProposalLine().getProduct().getDescription(),
      orderLine.getProposalLine().getProduct().getBrand().getId(),
      orderLine.getProposalLine().getProduct().getBrand().getName(),
      orderLine.getProposalLine().getProduct().getCategory().getId(),
      orderLine.getProposalLine().getProduct().getCategory().getName()
      ),
      orderLine.getProposalLine().getPrice()
      )
      ));
    }

    return lineResponse;
  }
}
