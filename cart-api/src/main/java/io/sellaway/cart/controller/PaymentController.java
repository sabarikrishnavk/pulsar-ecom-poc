package io.sellaway.cart.controller;
 
import io.sellaway.cart.CartConstants;
import io.sellaway.cart.objects.CartPayment;
import lombok.extern.log4j.Log4j2;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.pulsar.config.PulsarTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payment")
@Log4j2
public class PaymentController {

    @Autowired
    PulsarTemplate template;


    @PostMapping
    public ResponseEntity<String> addPayment(@RequestBody CartPayment payment) {
        log.info("Rest: cart-payment-add :{} ", payment );

        try {
            template.send(CartConstants.TOPIC_CART_PAYMENT_ADD,payment);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Added Payment", getHttpHeaders(), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<String> updatePayment(@RequestBody CartPayment payment) {
        log.info("Rest: cart-payment-update :{} ", payment );
        try {
            template.send(CartConstants.TOPIC_CART_PAYMENT_UPDATE,payment);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Updated Payment", getHttpHeaders(), HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<String> deletePayment(@RequestBody CartPayment payment) {
        log.info("Rest: cart-payment-delete :{} ", payment );
        try {
            template.send(CartConstants.TOPIC_CART_PAYMENT_DELETE,payment);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Deleted Payment", getHttpHeaders(), HttpStatus.OK);
    }

    private final HttpHeaders httpHeaders = new HttpHeaders();
    protected HttpHeaders getHttpHeaders() {
        this.httpHeaders.add("Content-Type", "application/json");
        return this.httpHeaders;

    }

}
