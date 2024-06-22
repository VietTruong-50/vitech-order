package vn.hust.api.dto.in;

import lombok.Builder;
import lombok.Data;
import vn.hust.api.dto.model.PaymentMethodEnum;

@Data
@Builder
public class CreateCardPaymentIn {
    private String cardNumber;
    private String year;
    private String cardOwner;
    private String month;
    private PaymentMethodEnum paymentMethodId;
}
