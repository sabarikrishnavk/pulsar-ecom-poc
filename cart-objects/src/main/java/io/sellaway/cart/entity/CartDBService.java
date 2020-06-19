package io.sellaway.cart.entity;

import io.sellaway.cart.entity.CartEntity;
import io.sellaway.cart.entity.CartRepository;
import io.sellaway.cart.objects.Cart;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Component
public class CartDBService {

    @Autowired
    public CartRepository repository;

    public void persistCart(Cart cart) {
        log.info("persisting cart to db");
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartId(cart.getOrderId());
        cartEntity.setCart(cart);
        repository.save(cartEntity);
    }

    public Cart findCart(String orderId) {

        Optional<CartEntity> entity = repository.findById(orderId);
        Cart cart = null;

        if(!entity.isEmpty()){
            cart = entity.get().getCart();
        }

        if(cart ==null){
            cart = new Cart();
            cart.setOrderId(orderId);
        }
        if(cart.getLineItems() == null){
            cart.setLineItems(new ArrayList<>());
        }
        return cart;
    }
}
