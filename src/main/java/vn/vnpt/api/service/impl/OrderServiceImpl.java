package vn.vnpt.api.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.vnpt.api.dto.address.CreateUpdateAddressIn;
import vn.vnpt.api.dto.in.CreateOrderIn;
import vn.vnpt.api.dto.in.cart.AddUpdateItemIn;
import vn.vnpt.api.dto.model.OrderStatusEnum;
import vn.vnpt.api.dto.out.cart.CartDetailOut;
import vn.vnpt.api.dto.out.order.OrderInformationOut;
import vn.vnpt.api.dto.out.order.OrderListOut;
import vn.vnpt.api.model.Notification;
import vn.vnpt.api.model.User;
import vn.vnpt.api.repository.AddressRepository;
import vn.vnpt.api.repository.NotificationRepository;
import vn.vnpt.api.repository.OrderRepository;
import vn.vnpt.api.repository.UserRepository;
import vn.vnpt.api.service.CartService;
import vn.vnpt.api.service.OrderService;
import vn.vnpt.api.service.VNPayService;
import vn.vnpt.common.Common;
import vn.vnpt.common.model.PagingOut;
import vn.vnpt.common.model.SortPageIn;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final KafkaProducerService kafkaProducerService;
    private final CartService cartService;
    private final NotificationRepository notificationRepository;


    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, AddressRepository addressRepository,
                            KafkaProducerService kafkaProducerService, CartService cartService, NotificationRepository notificationRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void checkOut(CreateOrderIn createOrderIn) {
        CartDetailOut cartDetailOut;

        try {
            //Get user id
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            Optional<User> user = userRepository.findByEmail(authentication.getName());
            String userId = user.get().getId();

            String orderCode = Common.getAlphaNumeric(12);

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

            //Get cart items
            cartDetailOut = cartService.getCartDetail();

            //Create order
            var orderId = Common.GenerateUUID();

            for (var item : cartDetailOut.getCart().values()) {
                var product = (AddUpdateItemIn) item;
                orderRepository.createOrderDetail(orderId, product);
            }

            orderRepository.createNewOrder(userId, createOrderIn, orderId, orderCode);

            //delete cart
            cartService.deleteCart();

            Notification notification = new Notification();

            notification.setOrderId(orderId);
            notification.setOrderCode(orderCode);
            notification.setCustomerId(userId);
            notification.setMessageContent("Đặt hàng thành công");
            notification.setCreatedDate(LocalDate.now().toString());
            notification.setUpdatedDate(LocalDate.now().toString());

            notificationRepository.save(notification);

            //Todo: Notification
//            kafkaProducerService.sendMessage("PurchaseTopic", "Order %s created successfully!".formatted("orderId"));
        } catch (Exception e) {
            e.printStackTrace();
//            kafkaProducerService.sendMessage("Exception", "Create order failed!");
        }
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
