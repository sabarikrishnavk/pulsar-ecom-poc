package io.sellaway.cart.service;

import io.sellaway.cart.CartConstants;
import io.sellaway.cart.objects.Cart;
import io.sellaway.cart.objects.CartContact;
import io.sellaway.cart.entity.CartDBService;
import org.springframework.integration.pulsar.annotation.PulsarConsumer;
import org.springframework.integration.pulsar.constant.Serialization;
import org.springframework.integration.pulsar.producer.PulsarTemplate;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ContactService {

    @Autowired
    PulsarTemplate template;

    @Autowired
    CartDBService cartDBService;

    @Autowired
    CalculationService  calculateService;

    @PulsarConsumer(topic=CartConstants.TOPIC_CART_CONTACT_ADD, clazz= CartContact.class, serialization = Serialization.JSON, type = SubscriptionType.Exclusive)
    public void addItem(CartContact contact) {
        log.info("Stream : cart-contact-add : " +contact.toString());
        processContact(contact);
    }


    @PulsarConsumer(topic=CartConstants.TOPIC_CART_CONTACT_UPDATE, clazz= CartContact.class, serialization = Serialization.JSON, type = SubscriptionType.Exclusive)
    public void updateItem(CartContact contact) {
        log.info("Stream : cart-contact-update : " +contact.toString());
        processContact(contact);
    }

    private void processContact(CartContact contact) {
        Cart cart = cartDBService.findCart( contact.getOrderId() );
        cart.setContact(contact);
        cartDBService.persistCart(cart);
        try {
            template.send(CartConstants.WEBHOOK_TOPIC_CART_SUMMARY,cart.getSummary());
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }


    @PulsarConsumer(topic=CartConstants.TOPIC_CART_CONTACT_DELETE, clazz= CartContact.class, serialization = Serialization.JSON, type = SubscriptionType.Exclusive)
    public void deleteItem(CartContact contact) {
        log.info("Stream : cart-contact-delete : " +contact.toString());
    }

}
