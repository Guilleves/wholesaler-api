package com.api.logic.business;

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
    private OrderDataAccess oda;

    public OrderLogic() {
      oda = new OrderDataAccess();
    }

    public ArrayList<GetOrderResponse> getOrders() throws ApiException {
        ArrayList<Order> orders = oda.getOrders();

        if (orders == null || orders.isEmpty())
            throw new ApiException("Cound't find any order.", Status.NOT_FOUND);

        ArrayList<GetOrderResponse> response = new ArrayList<GetOrderResponse>();

        for (Order order : orders) {
            ArrayList<GetOrderResponse.OrderLine> lineResponse = new ArrayList<GetOrderResponse.OrderLine>();

            for(OrderLine orderLine : order.getOrderLines()) {
                lineResponse.add(getOrderLine(orderLine));
            }

            response.add(new GetOrderResponse(
                order.getId(),
                order.getDateOrdered(),
                lineResponse,
                new GetOrganizationResponse(
                    order.getRetail().getId(),
                    order.getRetail().getName(),
                    order.getRetail().getLegalName(),
                    order.getRetail().getCuit(),
                    order.getRetail().getRole()
                )
            ));
        }

        return response;
    }

    public GetOrderResponse getOrder(GetOrderRequest request) throws ApiException {
        Order order = oda.getOrder(request.getOrderId());

        if (order == null)
            throw new ApiException("Cound't find any order.", Status.NOT_FOUND);

        ArrayList<GetOrderResponse.OrderLine> lineResponse = new ArrayList<GetOrderResponse.OrderLine>();

        for(OrderLine orderLine : order.getOrderLines()) {
            lineResponse.add(getOrderLine(orderLine));
        }

        return new GetOrderResponse(
            order.getId(),
            order.getDateOrdered(),
            lineResponse,
            new GetOrganizationResponse(
                order.getRetail().getId(),
                order.getRetail().getName(),
                order.getRetail().getLegalName(),
                order.getRetail().getCuit(),
                order.getRetail().getRole()
            )
        );
    }

    private GetOrderResponse.OrderLine getOrderLine(OrderLine orderLine) {
        return new GetOrderResponse.OrderLine(
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
                    orderLine.getProposalLine().getProduct().getBrand().getId(),
                    orderLine.getProposalLine().getProduct().getBrand().getName(),
                    orderLine.getProposalLine().getProduct().getCategory().getId(),
                    orderLine.getProposalLine().getProduct().getCategory().getName()
                ),
                orderLine.getProposalLine().getPrice()
            )
        );
    }
}
