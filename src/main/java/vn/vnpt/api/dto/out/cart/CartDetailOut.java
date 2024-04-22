package vn.vnpt.api.dto.out.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailOut {
    private Map<Object, Object> cart;
}
