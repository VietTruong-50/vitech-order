package vn.vnpt.api.dto.out.order;

import lombok.Data;
import vn.vnpt.api.repository.helper.Col;

@Data
public class OrderDetailOut {
    @Col("id")
    private String id;
    @Col("order_id")
    private String orderId;
    @Col("product_id")
    private String productId;
    @Col("name")
    private String name;
    @Col("feature_image_link")
    private String featureImageLink;
    @Col("product_code")
    private String productCode;
    @Col("quantity")
    private Integer quantity;
    @Col("item_price")
    private Long itemPrice;
    @Col("attribute")
    private String attribute;
}
