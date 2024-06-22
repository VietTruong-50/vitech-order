package vn.hust.api.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.hust.api.dto.in.CreateOrderIn;
import vn.hust.api.dto.model.OrderStatusEnum;
import vn.hust.api.dto.out.order.OrderInformationOut;
import vn.hust.api.dto.out.order.OrderListOut;
import vn.hust.common.model.PagingOut;
import vn.hust.common.model.SortPageIn;

import java.util.Map;

public interface OrderService {

    Object checkOut(CreateOrderIn createOrderIn, String orderInfo, HttpServletRequest request);

    void updateVnpayOrder(Map<String, String> queryParams, HttpServletResponse response);

    PagingOut<OrderListOut> getCurrentOrders(OrderStatusEnum status, SortPageIn sortPageIn);

    OrderInformationOut getOrderDetail(String orderCode);

}
