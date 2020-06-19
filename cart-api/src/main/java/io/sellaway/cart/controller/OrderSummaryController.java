package io.sellaway.cart.controller;

import io.sellaway.cart.CartConstants;
import io.sellaway.cart.objects.CartSummary;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class OrderSummaryController {

    //    -------------- WebSocket API ----------------
    @MessageMapping("/sendMessage")
    @SendTo(CartConstants.WEBHOOK_API_CART_SUMMARY +"/{cartId}")
    public CartSummary updateOrderSummary(@DestinationVariable String cartId, @Payload CartSummary message) {
        return message;
    }
}
