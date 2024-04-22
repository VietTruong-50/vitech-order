package vn.vnpt.api.dto.address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUpdateAddressIn {
    private String receiverName;

    private String phone;

    private String email;

    private String city;

    private String district;

    private String  subDistrict;

    private String specificAddress;

    private Boolean isLevant;
}
