package vn.vnpt.api.dto.in;

import lombok.Data;
import vn.vnpt.api.dto.model.OrderStatusEnum;
import vn.vnpt.api.dto.model.PaymentMethodEnum;
import vn.vnpt.api.dto.model.ShippingMethodEnum;

import java.time.LocalDate;

@Data
public class CreateOrderIn {
    private String addressId;
    private String receiverName;
    private String phone;
    private String email;
    private Long total;
    private PaymentMethodEnum paymentMethodEnum;
    private ShippingMethodEnum shippingMethodEnum;
}
