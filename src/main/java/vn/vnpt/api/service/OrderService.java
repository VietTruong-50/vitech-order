package vn.vnpt.api.service;

import jakarta.servlet.http.HttpServletResponse;
import vn.vnpt.api.dto.in.CreateOrderIn;
import vn.vnpt.api.dto.model.OrderStatusEnum;
import vn.vnpt.api.dto.out.order.OrderInformationOut;
import vn.vnpt.api.dto.out.order.OrderListOut;
import vn.vnpt.common.model.PagingOut;
import vn.vnpt.common.model.SortPageIn;

import java.util.Map;

public interface OrderService {

    void checkOut(CreateOrderIn createOrderIn, String orderInfo);

    void updateVnpayOrder(Map<String, String> queryParams, HttpServletResponse response);

    PagingOut<OrderListOut> getCurrentOrders(OrderStatusEnum status, SortPageIn sortPageIn);

    OrderInformationOut getOrderDetail(String orderCode);

}
