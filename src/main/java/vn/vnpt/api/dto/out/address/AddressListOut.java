package vn.vnpt.api.dto.out.address;

import lombok.Data;
import vn.vnpt.api.repository.helper.Col;

@Data
public class AddressListOut {
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
    @Col("is_default")
    private boolean isDefault;
    @Col("user_created")
    private String userCreated;
}
