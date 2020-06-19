package io.sellaway.cart;

import io.sellaway.cart.objects.CartAddress;
import io.sellaway.cart.objects.CartContact;
import io.sellaway.cart.objects.CartLineItem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.pulsar.producer.ProducerFactory;

@SpringBootApplication
public class CartApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApiApplication.class, args);
    }

    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
                .addProducer(CartConstants.TOPIC_CART_ITEM_ADD, CartLineItem.class)
                .addProducer(CartConstants.TOPIC_CART_ITEM_UPDATE, CartLineItem.class)
                .addProducer(CartConstants.TOPIC_CART_ITEM_DELETE, CartLineItem.class)

                .addProducer(CartConstants.TOPIC_CART_ADDRESS_ADD, CartAddress.class)
                .addProducer(CartConstants.TOPIC_CART_ADDRESS_UPDATE, CartAddress.class)
                .addProducer(CartConstants.TOPIC_CART_ADDRESS_DELETE, CartAddress.class)

                .addProducer(CartConstants.TOPIC_CART_CONTACT_ADD, CartContact.class)
                .addProducer(CartConstants.TOPIC_CART_CONTACT_UPDATE, CartContact.class)
                .addProducer(CartConstants.TOPIC_CART_CONTACT_DELETE, CartContact.class) ;
    }
}
