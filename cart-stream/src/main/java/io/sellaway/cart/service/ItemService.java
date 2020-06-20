package io.sellaway.cart.service;

import io.sellaway.cart.CartConstants;
import io.sellaway.cart.objects.Cart;
import io.sellaway.cart.objects.CartLineItem;
import io.sellaway.cart.entity.CartDBService;
import org.springframework.integration.pulsar.annotation.PulsarConsumer;
import org.springframework.integration.pulsar.bean.PulsarSchemaType;
import org.springframework.integration.pulsar.config.PulsarTemplate;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ItemService {

    @Autowired
    PulsarTemplate template;

    @Autowired
    CartDBService cartDBService;

    @Autowired
    CalculationService  calculateService;

    @PulsarConsumer(topic=CartConstants.TOPIC_CART_ITEM_ADD, clazz= CartLineItem.class,  type = SubscriptionType.Exclusive)
    public void addItem(CartLineItem item) {
        log.info("Stream : cart-item-add : " +item.toString());
        processItem(item);
    }


    @PulsarConsumer(topic=CartConstants.TOPIC_CART_ITEM_UPDATE, clazz= CartLineItem.class, type = SubscriptionType.Exclusive)
    public void updateItem(CartLineItem item) {
        log.info("Stream : cart-item-update : " +item.toString());

        processItem(item);
    }

    private void processItem(CartLineItem item) {
        Cart cart = cartDBService.findCart( item.getOrderId() );

        if(item.getLineNumber() == null){
            item.setLineNumber(item.getSku()+"-"+item.getShippingMethod());
        }

        Optional<CartLineItem> existingItem = cart.getLineItems().stream().filter(itemDB ->{
            return item.getLineNumber().equalsIgnoreCase(itemDB.getLineNumber());
        } ).findAny();

        if(!existingItem.isEmpty()){
            cart.getLineItems().remove(existingItem.get());
        }
        cart.getLineItems().add(item);

        calculateService.calculate(cart);

        cartDBService.persistCart(cart);

        try {
            template.send(CartConstants.WEBHOOK_TOPIC_CART_SUMMARY,cart.getSummary());
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }


    @PulsarConsumer(topic=CartConstants.TOPIC_CART_ITEM_DELETE, clazz= CartLineItem.class,   type = SubscriptionType.Exclusive)
    public void deleteItem(CartLineItem msg) {
        log.info("Stream : cart-item-delete : " +msg.toString());
    }

    @PulsarConsumer(topic=CartConstants.TOPIC_CART_LOGS, clazz=String.class)
    public void logPrint(String msg) {
        log.info("Stream : cart-log-stream : " +msg);
    }
}
