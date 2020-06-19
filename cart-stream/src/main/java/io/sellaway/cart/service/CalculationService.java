package io.sellaway.cart.service;

import io.sellaway.cart.objects.Cart;
import io.sellaway.cart.objects.CartLineItem;
import io.sellaway.cart.objects.CartSummary;
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
            summary.setSize(cart.getLineItems().size());
            summary.setTotalQty(cart.getLineItems().stream().mapToInt(CartLineItem::getQuantity).sum());
        }

        Stream<String> shippingZipCodes = cart.getLineItems().stream().filter(cartLineItem->{
            return !"BOPIS".equalsIgnoreCase(cartLineItem.getShippingMethod()) && cartLineItem.getZipCode() !=null ;
        }).map(CartLineItem::getZipCode).distinct();

        int count = (int) shippingZipCodes.count();
        if(count > 0){
            summary.setShipping(5.99);
        }
        cart.setSummary(summary);
    }
}
