package vn.hust.api.dto.in;

import lombok.Data;
import vn.hust.api.dto.model.PaymentMethodEnum;
import vn.hust.api.dto.model.ShippingMethodEnum;

@Data
public class CreateOrderIn {
    private String addressId;
    private String receiverName;
    private String phone;
    private String email;
    private String orderInfo;
    private Long total;
    private PaymentMethodEnum paymentMethodEnum;
    private ShippingMethodEnum shippingMethodEnum;
}
