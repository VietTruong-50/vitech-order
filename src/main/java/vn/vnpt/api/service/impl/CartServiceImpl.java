package vn.vnpt.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.vnpt.api.dto.out.cart.CartDetailOut;
import vn.vnpt.api.model.User;
import vn.vnpt.api.repository.UserRepository;
import vn.vnpt.api.service.CartService;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final HashOperations<Object, Object, Object> hashOperations;
    private final String CART_KEY = "user:cart";
    private final UserRepository userRepository;
    private final RedisTemplate<Object, Object> redisTemplate;

    CartServiceImpl(RedisTemplate<Object, Object> redisTemplate, UserRepository userRepository){
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public void deleteCart() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Optional<User> user = userRepository.findByEmail(authentication.getName());

        redisTemplate.delete(CART_KEY + ":" + user.get().getId());
    }

    @Override
    public CartDetailOut getCartDetail() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Optional<User> user = userRepository.findByEmail(authentication.getName());

        return user.map(value -> new CartDetailOut(hashOperations.entries(CART_KEY + ":" + value.getId()))).orElse(null);
    }
}
