package io.sellaway.cart.service;

import io.sellaway.cart.CartConstants;
import io.sellaway.cart.entity.CartDBService;
import io.sellaway.cart.objects.Cart;
import io.sellaway.cart.objects.CartPayment;
import io.sellaway.cart.objects.CartStatusType;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.pulsar.annotation.PulsarConsumer;
import org.springframework.integration.pulsar.config.PulsarTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class PaymentService {

    @Autowired
    PulsarTemplate template;

    @Autowired
    CartDBService cartDBService;

    @Autowired
    CalculationService  calculateService;

    @PulsarConsumer(topic=CartConstants.TOPIC_CART_PAYMENT_ADD, clazz= CartPayment.class,  type = SubscriptionType.Exclusive)
    public void addItem(CartPayment payment) {
        log.info("Stream : cart-payment-add : " +payment.toString());
        processPayment(payment);
    }


    @PulsarConsumer(topic=CartConstants.TOPIC_CART_PAYMENT_UPDATE, clazz= CartPayment.class, type = SubscriptionType.Exclusive)
    public void updateItem(CartPayment payment) {
        log.info("Stream : cart-payment-update : " +payment.toString());

        processPayment(payment);
    }

    private void processPayment(CartPayment payment) {
        Cart cart = cartDBService.findCart( payment.getOrderId().toString() );

        cart.getPayments().add(payment);

        calculateService.calculate(cart);

        cartDBService.persistCart(cart);

        try {
            template.send(CartConstants.WEBHOOK_TOPIC_CART_SUMMARY,cart.getSummary());
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }


    @PulsarConsumer(topic=CartConstants.TOPIC_CART_PAYMENT_DELETE, clazz= CartPayment.class,   type = SubscriptionType.Exclusive)
    public void deleteItem(CartPayment msg) {
        log.info("Stream : cart-payment-delete : " +msg.toString());
    }

    @PulsarConsumer(topic=CartConstants.TOPIC_CART_LOGS, clazz=String.class)
    public void logPrint(String msg) {
        log.info("Stream : cart-log-stream : " +msg);
    }
}
