package vn.vnpt.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import vn.vnpt.api.dto.in.CreateOrderIn;
import vn.vnpt.api.dto.model.OrderStatusEnum;
import vn.vnpt.api.service.OrderService;
import vn.vnpt.common.AbstractResponseController;
import vn.vnpt.common.model.SortPageIn;

import java.util.Collections;


@RequiredArgsConstructor
@RequestMapping("/v1/order")
@RestController
@Slf4j
public class OrderController extends AbstractResponseController {

    private final OrderService orderService;

    @PostMapping(value = "/checkout", produces = "application/json")
    public DeferredResult<ResponseEntity<?>> checkOut(@RequestBody CreateOrderIn createOrderIn) {
        return responseEntityDeferredResult(() -> {
            log.info("[REQUEST]: path: /v1/order/checkout");
            orderService.checkOut(createOrderIn);
            log.info("[RESPONSE]: res: Success!");
            return Collections.emptyMap();
        });
    }
}
