package io.sellaway.cart.entity;

import io.sellaway.cart.objects.Cart;
import io.sellaway.cart.objects.CartStatusType;
import org.springframework.integration.pulsar.util.AvroUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Log4j2
@Component
public class CartDBService {

    @Autowired
    public CartRepository repository;

    public void persistCart(Cart cart) {
        log.info("persisting cart to db");
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartId(cart.getOrderId().toString());
        try {
            cartEntity.setCart(AvroUtil.serializeBinary(cart,Cart.getClassSchema()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        repository.save(cartEntity);
    }

    public Cart findCart(String orderId) {
        
        Optional<CartEntity> entity = repository.findById(orderId);
        Cart cart = null;

        if(!entity.isEmpty()){
            try {
                cart= AvroUtil.deSerializeBinary(entity.get().getCart(),Cart.getClassSchema());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(cart ==null){
            cart = new Cart();
            cart.setOrderId(orderId);
        }
        if(cart.getLineItems() == null){
            cart.setLineItems(new ArrayList<>());
        }
        if(cart.getPayments() == null){
            cart.setPayments(new ArrayList<>());
        }
        cart.setStatus(CartStatusType.in_progress);

        return cart;
    }

}
