package io.sellaway.cart;

import io.sellaway.cart.objects.CartSummary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.pulsar.producer.ProducerFactory;

@SpringBootApplication
public class CartStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartStreamApplication.class, args);
	}
	@Bean
	public ProducerFactory producerFactory() {
		return new ProducerFactory()
				.addProducer(CartConstants.WEBHOOK_TOPIC_CART_SUMMARY, CartSummary.class) ;
	}
}
