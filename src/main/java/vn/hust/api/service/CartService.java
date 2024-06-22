package vn.hust.api.service;

import vn.hust.api.dto.out.cart.CartDetailOut;

public interface CartService {
    void deleteCart();

    CartDetailOut getCartDetail();
}
