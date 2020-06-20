package io.sellaway.cart;

import io.sellaway.cart.objects.CartAddress;
import io.sellaway.cart.objects.CartContact;
import io.sellaway.cart.objects.CartLineItem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.pulsar.registry.ProducerTopicRegistry;

@SpringBootApplication
public class CartApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApiApplication.class, args);
    }

    @Bean
    public ProducerTopicRegistry producerFactory() {
        return new ProducerTopicRegistry()
                .registerTopic(CartConstants.TOPIC_CART_ITEM_ADD, CartLineItem.class)
                .registerTopic(CartConstants.TOPIC_CART_ITEM_UPDATE, CartLineItem.class)
                .registerTopic(CartConstants.TOPIC_CART_ITEM_DELETE, CartLineItem.class)

                .registerTopic(CartConstants.TOPIC_CART_ADDRESS_ADD, CartAddress.class)
                .registerTopic(CartConstants.TOPIC_CART_ADDRESS_UPDATE, CartAddress.class)
                .registerTopic(CartConstants.TOPIC_CART_ADDRESS_DELETE, CartAddress.class)

                .registerTopic(CartConstants.TOPIC_CART_CONTACT_ADD, CartContact.class)
                .registerTopic(CartConstants.TOPIC_CART_CONTACT_UPDATE, CartContact.class)
                .registerTopic(CartConstants.TOPIC_CART_CONTACT_DELETE, CartContact.class) ;
    }
}
