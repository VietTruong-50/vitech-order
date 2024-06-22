package vn.hust.api.dto.out.payment;

import lombok.Data;
import vn.hust.api.repository.helper.Col;

@Data
public class CardPaymentDetailOut {
    @Col("card_payment_id")
    private String cardPaymentId;
}
