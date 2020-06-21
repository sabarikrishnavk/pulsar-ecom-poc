package io.sellaway.cart.controller;

import io.sellaway.cart.CartConstants;
import io.sellaway.cart.objects.LineItem;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.pulsar.config.PulsarTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/v1/item")
@Log4j2
public class ItemController {

    @Autowired
    PulsarTemplate template;


    @PostMapping
    public ResponseEntity<String> addItem(@RequestBody LineItem item) {
        log.info("Rest: cart-item-add :{} ", item );
        try {
            template.send(CartConstants.TOPIC_CART_ITEM_ADD,item);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Added Item", getHttpHeaders(), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<String> updateItem(@RequestBody LineItem cart) {
        log.info("Rest: cart-item-update :{} ", cart );
        try {
            template.send(CartConstants.TOPIC_CART_ITEM_UPDATE,cart);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Updated Item", getHttpHeaders(), HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteItem(@RequestBody LineItem cart) {
        log.info("Rest: cart-item-delete :{} ", cart );
        try {
            template.send(CartConstants.TOPIC_CART_ITEM_DELETE,cart);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Deleted Item", getHttpHeaders(), HttpStatus.OK);
    }

    private final HttpHeaders httpHeaders = new HttpHeaders();
    protected HttpHeaders getHttpHeaders() {
        this.httpHeaders.add("Content-Type", "application/json");
        return this.httpHeaders;

    }

}
