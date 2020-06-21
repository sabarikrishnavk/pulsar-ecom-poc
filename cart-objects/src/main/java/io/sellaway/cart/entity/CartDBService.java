package io.sellaway.cart.entity;

import io.sellaway.cart.objects.Cart;
import io.sellaway.cart.objects.CartStatusType;
import io.sellaway.cart.util.AvroUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Component
public class CartDBService {

    @Autowired
    public CartRepository repository;

    @Autowired
    public AvroUtil avroUtil;


    public void persistCart(Cart cart) {
        log.info("persisting cart to db");
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartId(cart.getOrderId().toString());
        cartEntity.setCart(avroUtil.serializeBinary(cart));
        repository.save(cartEntity);
    }

    public Cart findCart(String orderId) {
        
        Optional<CartEntity> entity = repository.findById(orderId);
        Cart cart = null;

        if(!entity.isEmpty()){

          cart= avroUtil.deSerializeBinary(entity.get().getCart());

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
