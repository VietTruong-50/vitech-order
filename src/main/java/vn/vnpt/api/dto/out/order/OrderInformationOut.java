package vn.vnpt.api.dto.out.order;

import lombok.Data;
import vn.vnpt.api.repository.helper.Col;

import java.util.List;

@Data
public class OrderInformationOut {
    @Col("order_id")
    private String orderId;

    @Col("invoice_symbol")
    private String invoiceSymbol;

    @Col("tax_number")
    private String taxNumber;

    @Col("tax_authorities_code")
    private String taxAuthoritiesCode;

    @Col("order_code")
    private String orderCode;

    @Col("total")
    private Long total;

    @Col("shipping_price")
    private Long shippingPrice;

    @Col("status")
    private String status;

    @Col("delivery_date")
    private String deliveryDate;  

    @Col("shipping_method")
    private String shippingMethod;

    @Col("payment_method")
    private String paymentMethod;

    @Col("address_id")
    private String addressId;

    @Col("receiver_name")
    private String receiverName;

    @Col("phone")
    private String phone;

    @Col("email")
    private String email;

    @Col("city")
    private String city;

    @Col("district")
    private String district;

    @Col("sub_district")
    private String subDistrict;

    @Col("specific_address")
    private String specificAddress;

    @Col("user_created")
    private String userCreated;

    @Col("created_date")
    private String createdDate;

    @Col("cancel_date")
    private String cancelDate;   

    @Col("success_date")
    private String successDate;  

    @Col("refund_date")
    private String refundDate;   

    @Col("confirm_date")
    private String confirmDate;

    @Col("payment_status")
    private Integer paymentStatus;


    private List<OrderDetailOut> orderDetailOuts;
}