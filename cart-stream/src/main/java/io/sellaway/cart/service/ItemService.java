package io.sellaway.cart.service;

import io.sellaway.cart.CartConstants;
import io.sellaway.cart.objects.Cart;
import io.sellaway.cart.objects.CartStatusType;
import io.sellaway.cart.objects.LineItem;
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

    @PulsarConsumer(topic=CartConstants.TOPIC_CART_ITEM_ADD, clazz= LineItem.class, schemaType = PulsarSchemaType.AVRO , type = SubscriptionType.Exclusive)
    public void addItem(LineItem item) {
        log.info("Stream : cart-item-add : " +item.toString());
        processItem(item);
    }


    @PulsarConsumer(topic=CartConstants.TOPIC_CART_ITEM_UPDATE, clazz= LineItem.class, schemaType = PulsarSchemaType.AVRO ,type = SubscriptionType.Exclusive)
    public void updateItem(LineItem item) {
        log.info("Stream : cart-item-update : " +item.toString());

        processItem(item);
    }

    private void processItem(LineItem item) {
        Cart cart = cartDBService.findCart( item.getOrderId().toString() );

        if(item.getLineNumber() == null){
            item.setLineNumber(cart.getOrderId()+""+item.getSku()+item.getShippingMethod());
        }

        Optional<LineItem> existingItem = cart.getLineItems().stream().filter(itemDB ->{
            return itemDB !=null && item.getLineNumber()==(itemDB.getLineNumber());
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


    @PulsarConsumer(topic=CartConstants.TOPIC_CART_ITEM_DELETE, clazz= LineItem.class,  schemaType = PulsarSchemaType.AVRO , type = SubscriptionType.Exclusive)
    public void deleteItem(LineItem msg) {
        log.info("Stream : cart-item-delete : " +msg.toString());
    }

    @PulsarConsumer(topic=CartConstants.TOPIC_CART_LOGS, clazz=String.class)
    public void logPrint(String msg) {
        log.info("Stream : cart-log-stream : " +msg);
    }
}
