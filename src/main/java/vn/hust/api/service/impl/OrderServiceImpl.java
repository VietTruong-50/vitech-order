package vn.hust.api.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.hust.api.dto.address.CreateUpdateAddressIn;
import vn.hust.api.dto.in.CreateOrderIn;
import vn.hust.api.dto.in.cart.AddUpdateItemIn;
import vn.hust.api.dto.model.OrderStatusEnum;
import vn.hust.api.dto.model.PaymentMethodEnum;
import vn.hust.api.dto.out.cart.CartDetailOut;
import vn.hust.api.dto.out.order.OrderInformationOut;
import vn.hust.api.dto.out.order.OrderListOut;
import vn.hust.api.model.Notification;
import vn.hust.api.model.User;
import vn.hust.api.repository.AddressRepository;
import vn.hust.api.repository.NotificationRepository;
import vn.hust.api.repository.OrderRepository;
import vn.hust.api.repository.UserRepository;
import vn.hust.api.service.CartService;
import vn.hust.api.service.OrderService;
import vn.hust.api.service.VNPayService;
import vn.hust.common.Common;
import vn.hust.common.model.PagingOut;
import vn.hust.common.model.SortPageIn;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final KafkaProducerService kafkaProducerService;
    private final CartService cartService;
    private final NotificationRepository notificationRepository;
    private final VNPayService vnPayService;
    private final HashOperations<Object, Object, Object> hashOperations;
    private final RedisTemplate<Object, Object> redisTemplate;
    private final String CART_KEY = "user:cart";


    public OrderServiceImpl(RedisTemplate<Object, Object> redisTemplate, UserRepository userRepository, OrderRepository orderRepository, AddressRepository addressRepository,
                            KafkaProducerService kafkaProducerService, CartService cartService, NotificationRepository notificationRepository,
                            VNPayService vnPayService) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.notificationRepository = notificationRepository;
        this.vnPayService = vnPayService;
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object checkOut(CreateOrderIn createOrderIn, String orderInfo, HttpServletRequest request) {
        try {
            //Get user id
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            Optional<User> user = userRepository.findByEmail(authentication.getName());
            String userId = user.get().getId();

            //Check && update address
            var address = addressRepository.getAddressDetail(createOrderIn.getAddressId());

            if (Common.isNullOrEmpty(address)) {
                var updateAddress = CreateUpdateAddressIn.builder()
                        .email(Common.defaultIfNullOrEmpty(createOrderIn.getEmail(), address.getEmail()))
                        .phone(Common.defaultIfNullOrEmpty(createOrderIn.getPhone(), address.getPhone()))
                        .receiverName(Common.defaultIfNullOrEmpty(createOrderIn.getReceiverName(), address.getReceiverName()))
                        .build();
                addressRepository.updateAddress(address.getAddressId(), updateAddress, userId);
            }

            //Create order
            var orderId = Common.GenerateUUID();

            if (createOrderIn.getPaymentMethodEnum().equals(PaymentMethodEnum.COD)) {
                updateCart(orderId, userId);

                orderRepository.createNewOrder(userId, createOrderIn, orderId, orderInfo);

                Notification notification = new Notification();

                notification.setOrderId(orderId);
                notification.setOrderCode(orderInfo);
                notification.setCustomerId(userId);
                notification.setMessageContent("Đặt hàng thành công");
                notification.setCreatedDate(LocalDate.now().toString());
                notification.setUpdatedDate(LocalDate.now().toString());

                notificationRepository.save(notification);
            } else if (createOrderIn.getPaymentMethodEnum().equals(PaymentMethodEnum.CREDIT_CARD)) {
                String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
                String vnpayUrl = vnPayService.createOrder(createOrderIn.getTotal(), orderInfo, baseUrl);

                orderRepository.createNewOrder(userId, createOrderIn, orderId, orderInfo);

                return vnpayUrl;
            }

//            kafkaProducerService.sendMessage("PurchaseTopic", "Order %s created successfully!".formatted("orderId"));
        } catch (Exception e) {
            e.printStackTrace();
//            kafkaProducerService.sendMessage("Exception", "Create order failed!");
        }
        return Collections.emptyMap();
    }

    @Override
    public void updateVnpayOrder(Map<String, String> queryParams, HttpServletResponse response) {
        try {
            var status = queryParams.get("vnp_ResponseCode");
            var orderInfo = queryParams.get("vnp_OrderInfo");
            var orderDetail = orderRepository.getOrderDetail(orderInfo);

            //Get user id
            String userId = orderDetail.getUserCreated();

            if (Objects.equals(status, "00")) {
                updateCart(orderDetail.getOrderId(), userId);

                //Todo: Notification
                Notification notification = new Notification();

                notification.setOrderId(orderDetail.getOrderId());
                notification.setOrderCode(orderInfo);
                notification.setCustomerId(userId);
                notification.setMessageContent("Đặt hàng thành công");
                notification.setCreatedDate(LocalDate.now().toString());
                notification.setUpdatedDate(LocalDate.now().toString());

                notificationRepository.save(notification);

                response.sendRedirect("http://localhost:4200/payment-completed?orderCode=" + orderInfo);
            } else {
                orderRepository.deleteOrder(orderDetail.getOrderId());
                response.sendRedirect("http://localhost:4200/checkout");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCart(String orderId, String userId) {
        //Get cart items
        CartDetailOut cartDetailOut = new CartDetailOut(hashOperations.entries(CART_KEY + ":" + userId));

        for (var item : cartDetailOut.getCart().values()) {
            var product = (AddUpdateItemIn) item;
            orderRepository.createOrderDetail(orderId, product);
        }

        //delete cart
        redisTemplate.delete(CART_KEY + ":" + userId);
    }

    @Override
    public PagingOut<OrderListOut> getCurrentOrders(OrderStatusEnum status, SortPageIn sortPageIn) {
        return orderRepository.getCurrentOrders(status, sortPageIn);
    }

    @Override
    public OrderInformationOut getOrderDetail(String orderCode) {
        var rs = orderRepository.getOrderDetail(orderCode);
        rs.setOrderDetailOuts(orderRepository.getOrderDetails(orderCode));
        return rs;
    }

}
