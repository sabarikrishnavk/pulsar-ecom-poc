package io.sellaway.cart;

import io.sellaway.cart.objects.CartAddress;
import io.sellaway.cart.objects.CartContact;
import io.sellaway.cart.objects.LineItem;
import io.sellaway.cart.objects.CartPayment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.pulsar.bean.PulsarSchemaType;
import org.springframework.integration.pulsar.registry.ProducerTopicRegistry;

@SpringBootApplication
public class CartApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApiApplication.class, args);
    }

    @Bean
    public ProducerTopicRegistry producerFactory() {
        return new ProducerTopicRegistry()
                .registerTopic(CartConstants.TOPIC_CART_ITEM_ADD, LineItem.class, PulsarSchemaType.AVRO)
                .registerTopic(CartConstants.TOPIC_CART_ITEM_UPDATE, LineItem.class, PulsarSchemaType.AVRO)
                .registerTopic(CartConstants.TOPIC_CART_ITEM_DELETE, LineItem.class, PulsarSchemaType.AVRO)

                .registerTopic(CartConstants.TOPIC_CART_ADDRESS_ADD, CartAddress.class, PulsarSchemaType.AVRO)
                .registerTopic(CartConstants.TOPIC_CART_ADDRESS_UPDATE, CartAddress.class, PulsarSchemaType.AVRO)
                .registerTopic(CartConstants.TOPIC_CART_ADDRESS_DELETE, CartAddress.class, PulsarSchemaType.AVRO)

                .registerTopic(CartConstants.TOPIC_CART_CONTACT_ADD, CartContact.class, PulsarSchemaType.AVRO)
                .registerTopic(CartConstants.TOPIC_CART_CONTACT_UPDATE, CartContact.class, PulsarSchemaType.AVRO)
                .registerTopic(CartConstants.TOPIC_CART_CONTACT_DELETE, CartContact.class, PulsarSchemaType.AVRO)

                .registerTopic(CartConstants.TOPIC_CART_PAYMENT_ADD, CartPayment.class, PulsarSchemaType.AVRO)
                .registerTopic(CartConstants.TOPIC_CART_PAYMENT_UPDATE, CartPayment.class, PulsarSchemaType.AVRO)
                .registerTopic(CartConstants.TOPIC_CART_PAYMENT_DELETE, CartPayment.class, PulsarSchemaType.AVRO) ;
    }
}
