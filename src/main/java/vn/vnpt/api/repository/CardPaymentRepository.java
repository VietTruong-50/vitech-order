package vn.vnpt.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.vnpt.api.dto.in.CreateCardPaymentIn;
import vn.vnpt.api.dto.out.payment.CardPaymentDetailOut;
import vn.vnpt.api.repository.helper.ProcedureCallerV3;
import vn.vnpt.api.repository.helper.ProcedureParameter;
import vn.vnpt.common.constant.DatabaseStatus;
import vn.vnpt.common.exception.NotFoundException;

import java.util.List;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class CardPaymentRepository {

    private final ProcedureCallerV3 procedureCallerV3;

    public CardPaymentDetailOut getCardDetail(String userId) {
        var outputs = procedureCallerV3.callOneRefCursor("card_payment_detail",
                List.of(
                        ProcedureParameter.inputParam("prs_user_id", String.class, userId),
                        ProcedureParameter.outputParam("out_result", String.class),
                        ProcedureParameter.refCursorParam("out_cur")
                ),
                CardPaymentDetailOut.class
        );
        List<CardPaymentDetailOut> outList = (List<CardPaymentDetailOut>) outputs.get("out_cur");

        return !outList.isEmpty() ? outList.get(0) : null;
    }

    public String createNewCardPayment(CreateCardPaymentIn createCardPaymentIn, String userId) {
        var outputs = procedureCallerV3.callNoRefCursor("card_payment_create_new", List.of(
                ProcedureParameter.inputParam("prs_card_number", String.class, createCardPaymentIn.getCardNumber()),
                ProcedureParameter.inputParam("prs_year", String.class, createCardPaymentIn.getYear()),
                ProcedureParameter.inputParam("prs_card_owner", String.class, createCardPaymentIn.getCardOwner()),
                ProcedureParameter.inputParam("prs_month", String.class, createCardPaymentIn.getMonth()),
                ProcedureParameter.inputParam("prs_payment_method_id", String.class, createCardPaymentIn.getPaymentMethodId()),
                ProcedureParameter.inputParam("prs_user_id", String.class, userId),
                ProcedureParameter.outputParam("out_card_payment_id", String.class),
                ProcedureParameter.outputParam("out_result", String.class))
        );

        String result = (String) outputs.get("out_result");
        if (!DatabaseStatus.Success.equals(result)) throw new RuntimeException("Call card_payment_create_new failed!");
        return (String) outputs.get("out_card_payment_id");
    }
}
