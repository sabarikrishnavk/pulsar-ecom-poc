package io.sellaway.cart.controller;

import io.sellaway.cart.CartConstants;
import io.sellaway.cart.objects.CartContact;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.pulsar.producer.PulsarTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/contact")
@Log4j2
public class ContactController {

    @Autowired
    PulsarTemplate template;
    @PostMapping
    public ResponseEntity<String> addContact(@RequestBody CartContact contact) {
        log.info("Rest: cart-contact-add :{} ", contact );
        try {
            template.send(CartConstants.TOPIC_CART_CONTACT_ADD ,contact);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Added contact", getHttpHeaders(), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<String> updateContact(@RequestBody CartContact contact) {
        log.info("Rest: cart-contact-update :{} ", contact );
        try {
            template.send(CartConstants.TOPIC_CART_CONTACT_UPDATE,contact);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Updated contact", getHttpHeaders(), HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteContact(@RequestBody CartContact contact) {
        log.info("Rest: cart-contact-delete :{} ", contact );
        try {
            template.send(CartConstants.TOPIC_CART_CONTACT_DELETE,contact);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Deleted contact", getHttpHeaders(), HttpStatus.OK);
    }

    private final HttpHeaders httpHeaders = new HttpHeaders();
    protected HttpHeaders getHttpHeaders() {
        this.httpHeaders.add("Content-Type", "application/json");
        return this.httpHeaders;

    }

}
