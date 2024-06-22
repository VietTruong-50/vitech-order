package vn.hust.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vn.hust.api.dto.address.CreateUpdateAddressIn;
import vn.hust.api.dto.out.address.AddressDetailOut;
import vn.hust.api.dto.out.address.AddressListOut;
import vn.hust.api.repository.helper.ProcedureCallerV3;
import vn.hust.api.repository.helper.ProcedureParameter;
import vn.hust.common.constant.DatabaseStatus;
import vn.hust.common.exception.NotFoundException;
import vn.hust.common.model.PagingOut;
import vn.hust.common.model.SortPageIn;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class AddressRepository {
    private final ProcedureCallerV3 procedureCallerV3;

    public String createNewAddress(CreateUpdateAddressIn addressRequest, String userId) {
        Map<String, Object> outputs = procedureCallerV3.callNoRefCursor("address_create_new",
                List.of(
                        ProcedureParameter.inputParam("prs_receiver_name", String.class, addressRequest.getReceiverName()),
                        ProcedureParameter.inputParam("prs_phone", String.class, addressRequest.getPhone()),
                        ProcedureParameter.inputParam("prs_email", String.class, addressRequest.getEmail()),
                        ProcedureParameter.inputParam("prs_city", String.class, addressRequest.getCity()),
                        ProcedureParameter.inputParam("prs_district", String.class, addressRequest.getDistrict()),
                        ProcedureParameter.inputParam("prs_sub_district", String.class, addressRequest.getSubDistrict()),
                        ProcedureParameter.inputParam("prs_specific_address", String.class, addressRequest.getSpecificAddress()),
                        ProcedureParameter.inputParam("prs_is_default", Boolean.class, addressRequest.getIsLevant()),
                        ProcedureParameter.inputParam("prs_user_id", String.class, userId),
                        ProcedureParameter.outputParam("out_address_id", String.class),
                        ProcedureParameter.outputParam("out_result", String.class)
                )
        );
        String result = (String) outputs.get("out_result");
        if (!DatabaseStatus.Success.equals(result)) throw new RuntimeException("address_create_new failed!");
        return (String) outputs.get("out_address_id");
    }

    public void updateAddress(String addressId, CreateUpdateAddressIn addressRequest, String userId) {
        Map<String, Object> outputs = procedureCallerV3.callNoRefCursor("address_update",
                List.of(
                        ProcedureParameter.inputParam("prs_address_id", String.class, addressId),
                        ProcedureParameter.inputParam("prs_receiver_name", String.class, addressRequest.getReceiverName()),
                        ProcedureParameter.inputParam("prs_phone", String.class, addressRequest.getPhone()),
                        ProcedureParameter.inputParam("prs_email", String.class, addressRequest.getEmail()),
                        ProcedureParameter.inputParam("prs_city", String.class, addressRequest.getCity()),
                        ProcedureParameter.inputParam("prs_district", String.class, addressRequest.getDistrict()),
                        ProcedureParameter.inputParam("prs_sub_district", String.class, addressRequest.getSubDistrict()),
                        ProcedureParameter.inputParam("prs_specific_address", String.class, addressRequest.getSpecificAddress()),
                        ProcedureParameter.inputParam("prs_is_default", Boolean.class, addressRequest.getIsLevant()),
                        ProcedureParameter.inputParam("prs_user_id", String.class, userId),
                        ProcedureParameter.outputParam("out_result", String.class)
                )
        );
        String result = (String) outputs.get("out_result");
        if (!DatabaseStatus.Success.equals(result)) throw new RuntimeException("address_update failed!");
    }

    public PagingOut<AddressListOut> listAllAddress(String userId, SortPageIn sortPageIn) {
        Map<String, Object> outputs = procedureCallerV3.callOneRefCursor("address_list_filter",
                List.of(
                        ProcedureParameter.inputParam("prs_user_id", String.class, userId),
                        ProcedureParameter.inputParam("prs_properties_sort", String.class, sortPageIn.getPropertiesSort()),
                        ProcedureParameter.inputParam("prs_sort", String.class, sortPageIn.getSort()),
                        ProcedureParameter.inputParam("prn_page_index", Integer.class, sortPageIn.getPage()),
                        ProcedureParameter.inputParam("prn_page_size", Integer.class, sortPageIn.getMaxSize()),
                        ProcedureParameter.inputParam("prs_key_search", String.class, sortPageIn.getKeySearch()),
                        ProcedureParameter.outputParam("out_total", Long.class),
                        ProcedureParameter.outputParam("out_result", String.class),
                        ProcedureParameter.refCursorParam("out_cur")
                ), AddressListOut.class
        );

        var outList =  (List<AddressListOut>) outputs.get("out_cur");

        return PagingOut.of((Number) outputs.get("out_total"), sortPageIn, outList);
    }

    public AddressDetailOut getAddressDetail(String addressId) {
        Map<String, Object> outputs = procedureCallerV3.callOneRefCursor("address_detail",
                List.of(
                        ProcedureParameter.inputParam("prs_address_id", String.class, addressId),
                        ProcedureParameter.outputParam("out_result", String.class),
                        ProcedureParameter.refCursorParam("out_cur")
                ), AddressDetailOut.class
        );

        List<AddressDetailOut> outList = (List<AddressDetailOut>) outputs.get("out_cur");

        if (outList == null || outList.isEmpty()) {
            throw new NotFoundException("Address not found!");
        }

        return outList.get(0);
    }
}
