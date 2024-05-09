package vn.vnpt.api.service;

import jakarta.servlet.http.HttpServletRequest;

public interface VNPayService {
    String createOrder(long total, String orderInfor, String urlReturn);

    int orderReturn(HttpServletRequest request);
}
