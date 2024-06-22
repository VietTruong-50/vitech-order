package vn.hust.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import vn.hust.api.dto.in.CreateOrderIn;
import vn.hust.api.service.OrderService;
import vn.hust.common.AbstractResponseController;

import java.util.Map;


@RequiredArgsConstructor
@RequestMapping(value = "/v1/order")
@RestController
@Slf4j
public class OrderController extends AbstractResponseController {

    private final OrderService orderService;

    @PostMapping(value = "/checkout", produces = "application/json")
    public DeferredResult<ResponseEntity<?>> checkOut(@RequestBody CreateOrderIn createOrderIn, @RequestParam("orderInfo") String orderInfo, HttpServletRequest request) {
        return responseEntityDeferredResult(() -> {
            log.info("[REQUEST]: path: /v1/order/checkout");
            var rs = orderService.checkOut(createOrderIn, orderInfo, request);
            log.info("[RESPONSE]: res: Success!");
            return rs;
        });
    }


    @GetMapping("/payment-callback")
    public void paymentCallback(@RequestParam Map<String, String> queryParams, HttpServletResponse response) {
        orderService.updateVnpayOrder(queryParams, response);
    }
}
