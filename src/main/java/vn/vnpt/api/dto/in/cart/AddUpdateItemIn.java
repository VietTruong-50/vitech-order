package vn.vnpt.api.dto.in.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddUpdateItemIn {
    private String sessionToken;

    @NotBlank
    private String productId;

    @NotBlank
    private String productName;

    @NotNull
    private int quantity = 1;

    @NotNull
    private Long price;

    @NotNull
    private Long actualPrice;

    private String image;

    private String attribute;
}
