package vn.vnpt.api.dto.out.order;

import lombok.Data;
import vn.vnpt.api.repository.helper.Col;

@Data
public class OrderListOut {

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

    @Col("status")
    private String status;

    @Col("username")
    private String username;

    @Col("created_date")
    private String createdDate;

    @Col("updated_date")
    private String updatedDate;
}
