package io.sellaway.cart.service;

import io.sellaway.cart.objects.Cart;
import io.sellaway.cart.objects.LineItem;
import io.sellaway.cart.objects.CartSummary;
import io.sellaway.cart.objects.ShippingMethodType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@Log4j2
public class CalculationService {

    public void calculate(Cart cart) {
        log.info("Service : cart-calculator : " +cart.toString());
        CartSummary summary = new CartSummary();
        summary.setOrderId(cart.getOrderId());
        if(cart.getLineItems()!=null){
            summary.setLines(cart.getLineItems().size());
            summary.setQuantity(cart.getLineItems().stream().mapToDouble(LineItem::getQuantity).sum());
        }

        Stream<String> shippingZipCodes = cart.getLineItems().stream().filter(cartLineItem->{
            return ShippingMethodType.bopis == cartLineItem.getShippingMethod() && cartLineItem.getFulfillZipCode() !=null ;
        }).map(lineItem -> {
            return lineItem.getFulfillZipCode().toString();
        }).distinct();

        int count = (int) shippingZipCodes.count();
        if(count > 0){
            summary.setShipping(5.99);
        }
        cart.setSummary(summary);
    }
}
