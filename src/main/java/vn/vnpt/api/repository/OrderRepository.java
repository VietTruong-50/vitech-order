package vn.vnpt.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.vnpt.api.dto.in.CreateOrderIn;
import vn.vnpt.api.dto.model.OrderStatusEnum;
import vn.vnpt.api.dto.out.order.OrderDetailOut;
import vn.vnpt.api.dto.out.order.OrderInformationOut;
import vn.vnpt.api.dto.out.order.OrderListOut;
import vn.vnpt.api.repository.helper.ProcedureCallerV3;
import vn.vnpt.api.repository.helper.ProcedureParameter;
import vn.vnpt.common.Common;
import vn.vnpt.common.constant.DatabaseStatus;
import vn.vnpt.common.exception.NotFoundException;
import vn.vnpt.common.model.PagingOut;
import vn.vnpt.common.model.SortPageIn;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class OrderRepository {

    private final String invoiceSymbol = "1C22THN";

    //MST
    private final String taxCode = "5372756200-722";

    private final String taxAuthoritiesCode = "008B2B4F96C27C41F0BEB38CC14790AAB9";

    private final ProcedureCallerV3 procedureCallerV3;

    public void createNewOrder(String userCreated, CreateOrderIn createOrderIn, String orderId) {
        var outputs = procedureCallerV3.callNoRefCursor("order_create_new", List.of(
                ProcedureParameter.inputParam("prs_order_id", String.class, orderId),
                ProcedureParameter.inputParam("prs_invoice_symbol", String.class, invoiceSymbol),
                ProcedureParameter.inputParam("prs_tax_number", String.class, taxCode),
                ProcedureParameter.inputParam("prs_tax_authorities_code", String.class, taxAuthoritiesCode),
                ProcedureParameter.inputParam("prs_order_code", String.class, Common.getAlphaNumeric(12)),
                ProcedureParameter.inputParam("prs_user_id", String.class, userCreated),
                ProcedureParameter.inputParam("prs_status", String.class, OrderStatusEnum.PENDING),
                ProcedureParameter.inputParam("prs_total", Long.class, createOrderIn.getTotal()),
                ProcedureParameter.inputParam("prs_shipping_method", String.class, createOrderIn.getShippingMethodEnum()),
                ProcedureParameter.inputParam("prs_payment_method", String.class, createOrderIn.getPaymentMethodEnum()),
                ProcedureParameter.inputParam("prs_address_id", String.class, createOrderIn.getAddressId()),
                ProcedureParameter.outputParam("out_result", String.class))
        );

        String result = (String) outputs.get("out_result");
        if (!DatabaseStatus.Success.equals(result)) throw new RuntimeException("Create subcategory failed!");
    }

    public void createOrderDetail(String orderId, String productId, int quantity, Long price) {
        var outputs = procedureCallerV3.callNoRefCursor("order_detail_create_new", List.of(
                ProcedureParameter.inputParam("prs_order_id", String.class, orderId),
                ProcedureParameter.inputParam("prs_product_id", String.class, productId),
                ProcedureParameter.inputParam("prs_quantity", Integer.class, quantity),
                ProcedureParameter.inputParam("prs_item_price", Long.class, price),
                ProcedureParameter.outputParam("out_result", String.class))
        );

        String result = (String) outputs.get("out_result");
        if (!DatabaseStatus.Success.equals(result)) throw new RuntimeException("Call order_detail_create_new failed!");
    }

    public PagingOut<OrderListOut> getCurrentOrders(OrderStatusEnum status, SortPageIn sortPageIn) {
        Map<String, Object> outputs = procedureCallerV3.callOneRefCursor("order_list_filter",
                List.of(
                        ProcedureParameter.inputParam("prs_status", String.class, status),
                        ProcedureParameter.inputParam("prs_properties_sort", String.class, sortPageIn.getPropertiesSort()),
                        ProcedureParameter.inputParam("prs_sort", String.class, sortPageIn.getSort()),
                        ProcedureParameter.inputParam("prn_page_index", Integer.class, sortPageIn.getPage()),
                        ProcedureParameter.inputParam("prn_page_size", Integer.class, sortPageIn.getMaxSize()),
                        ProcedureParameter.inputParam("prs_key_search", String.class, sortPageIn.getKeySearch()),
                        ProcedureParameter.outputParam("out_total", Long.class),
                        ProcedureParameter.outputParam("out_result", String.class),
                        ProcedureParameter.refCursorParam("out_cur")
                ), OrderListOut.class
        );

        var outList = (List<OrderListOut>) outputs.get("out_cur");

        return PagingOut.of((Number) outputs.get("out_total"), sortPageIn, outList);
    }

    public OrderInformationOut getOrderDetail(String orderCode) {
        var outputs = procedureCallerV3.callOneRefCursor("order_detail_by_code",
                List.of(
                        ProcedureParameter.inputParam("prs_order_code", String.class, orderCode),
                        ProcedureParameter.outputParam("out_result", String.class),
                        ProcedureParameter.refCursorParam("out_cur")
                ),
                OrderInformationOut.class
        );
        List<OrderInformationOut> outList = (List<OrderInformationOut>) outputs.get("out_cur");
        if (outList == null || outList.isEmpty()) {
            throw new NotFoundException("call order_code_detail failed!");
        }

        return outList.get(0);
    }

    public List<OrderDetailOut> getOrderDetails(String orderCode) {
        var outputs = procedureCallerV3.callOneRefCursor("order_detail_list",
                List.of(
                        ProcedureParameter.inputParam("prs_order_code", String.class, orderCode),
                        ProcedureParameter.outputParam("out_result", String.class),
                        ProcedureParameter.refCursorParam("out_cur")
                ),
                OrderDetailOut.class
        );
        List<OrderDetailOut> outList = (List<OrderDetailOut>) outputs.get("out_cur");
        if (outList == null || outList.isEmpty()) {
            throw new NotFoundException("call order_detail_list failed!");
        }
        return outList;
    }
}
