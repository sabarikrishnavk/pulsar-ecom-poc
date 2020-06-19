package io.sellaway.cart.controller;

import io.sellaway.cart.CartConstants;
import io.sellaway.cart.objects.CartAddress;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.pulsar.producer.PulsarTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/address")
@Log4j2
public class AddressController {

    @Autowired
    PulsarTemplate template;
    @PostMapping
    public ResponseEntity<String> addAddress(@RequestBody CartAddress address) {
        log.info("Rest: cart-address-add :{} ", address );
        try {
            template.send(CartConstants.TOPIC_CART_ADDRESS_ADD ,address);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Added address", getHttpHeaders(), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<String> updateAddress(@RequestBody CartAddress address) {
        log.info("Rest: cart-address-update :{} ", address );
        try {
            template.send(CartConstants.TOPIC_CART_ADDRESS_UPDATE,address);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Updated address", getHttpHeaders(), HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteAddress(@RequestBody CartAddress address) {
        log.info("Rest: cart-address-delete :{} ", address );
        try {
            template.send(CartConstants.TOPIC_CART_ADDRESS_DELETE,address);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Deleted address", getHttpHeaders(), HttpStatus.OK);
    }

    private final HttpHeaders httpHeaders = new HttpHeaders();
    protected HttpHeaders getHttpHeaders() {
        this.httpHeaders.add("Content-Type", "application/json");
        return this.httpHeaders;

    }

}
