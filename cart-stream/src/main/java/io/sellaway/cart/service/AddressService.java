package io.sellaway.cart.service;

import io.sellaway.cart.CartConstants;
import io.sellaway.cart.objects.AddressType;
import io.sellaway.cart.objects.Cart;
import io.sellaway.cart.objects.CartAddress;
import io.sellaway.cart.entity.CartDBService;
import io.sellaway.cart.objects.ShippingMethodType;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.pulsar.annotation.PulsarConsumer;
import org.springframework.integration.pulsar.bean.PulsarSchemaType;
import org.springframework.integration.pulsar.config.PulsarTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
public class AddressService {

    @Autowired
    PulsarTemplate template;

    @Autowired
    CartDBService cartDBService;

    @Autowired
    CalculationService  calculateService;

    @PulsarConsumer(topic=CartConstants.TOPIC_CART_ADDRESS_ADD, clazz= CartAddress.class, type = SubscriptionType.Exclusive)
    public void addItem(CartAddress address) {
        log.info("Stream : cart-address-add : " +address.toString());
        processAddress(address);
    }


    @PulsarConsumer(topic=CartConstants.TOPIC_CART_ADDRESS_UPDATE, clazz= CartAddress.class, type = SubscriptionType.Exclusive)
    public void updateItem(CartAddress address) {
        log.info("Stream : cart-address-update : " +address.toString());
        processAddress(address);
    }

    private void processAddress(CartAddress address) {
        Cart cart = cartDBService.findCart( address.getOrderId().toString() );

        if(AddressType.shipping == address.getAddressType() || AddressType.shippingbilling == address.getAddressType() ) {
            cart.getLineItems().stream().forEach(cartLineItem -> {
                if(ShippingMethodType.bopis != cartLineItem.getShippingMethod()){
//                    cartLineItem.setAddressId(address.getAddressId());
//                    cartLineItem.setZipCode(address.getZipCode());
                }
            });
            cart.setShipAddress(address);
        }
        if(AddressType.billing == address.getAddressType() || AddressType.shippingbilling == address.getAddressType() ) {
            cart.setBillAddress(address);
        }


        cartDBService.persistCart(cart);

        calculateService.calculate(cart);
        try {
            template.send(CartConstants.WEBHOOK_TOPIC_CART_SUMMARY,cart.getSummary());
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }


    @PulsarConsumer(topic=CartConstants.TOPIC_CART_ADDRESS_DELETE, clazz= CartAddress.class, type = SubscriptionType.Exclusive)
    public void deleteItem(CartAddress address) {
        log.info("Stream : cart-address-delete : " +address.toString());
    }

}
