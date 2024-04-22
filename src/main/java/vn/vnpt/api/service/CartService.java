package vn.vnpt.api.service;

import vn.vnpt.api.dto.out.cart.CartDetailOut;

public interface CartService {
    void deleteCart();

    CartDetailOut getCartDetail();
}
