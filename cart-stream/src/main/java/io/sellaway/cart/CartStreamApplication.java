package io.sellaway.cart;

import io.sellaway.cart.objects.CartSummary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.pulsar.bean.PulsarSchemaType;
import org.springframework.integration.pulsar.registry.ProducerTopicRegistry;

@SpringBootApplication
public class CartStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartStreamApplication.class, args);
	}
	@Bean
	public ProducerTopicRegistry producerFactory() {
		return new ProducerTopicRegistry()
				.registerTopic(CartConstants.WEBHOOK_TOPIC_CART_SUMMARY, CartSummary.class, PulsarSchemaType.JSON) ;
	}
}
