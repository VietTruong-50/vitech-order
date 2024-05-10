package vn.vnpt.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import vn.vnpt.api.dto.in.CreateOrderIn;
import vn.vnpt.api.service.OrderService;
import vn.vnpt.api.service.VNPayService;
import vn.vnpt.common.AbstractResponseController;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;


@RequiredArgsConstructor
@RequestMapping(value = "/v1/order")
@RestController
@Slf4j
public class OrderController extends AbstractResponseController {

    private final OrderService orderService;
    private final VNPayService vnPayService;

    @PostMapping(value = "/checkout", produces = "application/json")
    public DeferredResult<ResponseEntity<?>> checkOut(@RequestBody CreateOrderIn createOrderIn, @RequestParam("orderInfo") String orderInfo) {
        return responseEntityDeferredResult(() -> {
            log.info("[REQUEST]: path: /v1/order/checkout");
            orderService.checkOut(createOrderIn, orderInfo);
            log.info("[RESPONSE]: res: Success!");
            return Collections.emptyMap();
        });
    }


    @PostMapping(value = "/submitOrder", produces = "application/json")
    public DeferredResult<ResponseEntity<?>> submitOrder(@RequestParam("amount") long orderTotal,
                                                         @RequestParam("orderInfo") String orderInfo,
                                                         HttpServletRequest request) {
        return responseEntityDeferredResult(() -> {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
//            orderService.checkOut(createOrderIn, orderInfo);
            return "redirect:" + vnpayUrl;
        });
    }

    @GetMapping("/payment-callback")
    public void paymentCallback(@RequestParam Map<String, String> queryParams, HttpServletResponse response) throws IOException {
        System.out.println(queryParams);

        orderService.updateVnpayOrder(queryParams, response);
    }
}
