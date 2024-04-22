package vn.vnpt.api.dto.out.cart;

import lombok.Data;

@Data
public class AddUpdateItemIn {
    private String sessionToken;

    private String productId;

    private String productName;

    private int quantity;

    private Long price;
}
