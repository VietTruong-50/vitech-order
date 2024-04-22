package vn.vnpt.api.dto.out.payment;

import lombok.Data;
import vn.vnpt.api.repository.helper.Col;

@Data
public class CardPaymentDetailOut {
    @Col("card_payment_id")
    private String cardPaymentId;
}
