package io.sellaway.cart.service;

import io.sellaway.cart.CartConstants;
import io.sellaway.cart.objects.CartSummary;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.pulsar.annotation.PulsarConsumer;
import org.springframework.integration.pulsar.constant.Serialization;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderSummaryService {
    @Autowired
    SimpMessagingTemplate template;

    @PulsarConsumer(topic = CartConstants.WEBHOOK_TOPIC_CART_SUMMARY,
            clazz = CartSummary.class, serialization = Serialization.JSON,
            type = SubscriptionType.Exclusive)
    public void orderSummary(CartSummary cartSummary) {
        log.info("Webhook : cart-summary : " +cartSummary.toString());

        template.convertAndSend(CartConstants.WEBHOOK_API_CART_SUMMARY + cartSummary.getOrderId(), cartSummary.toString());

    }
}
