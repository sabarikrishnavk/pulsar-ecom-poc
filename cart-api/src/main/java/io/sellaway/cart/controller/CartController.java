package io.sellaway.cart.controller;

import io.sellaway.cart.objects.Cart;
import io.sellaway.cart.entity.CartDBService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cart")
@Log4j2
public class CartController {

    @Autowired
    CartDBService cartDBService;
    @GetMapping(value = "/{orderId}")
    public ResponseEntity<String> getCart(@PathVariable("orderId") String orderId) {
        Cart cart = cartDBService.findCart(orderId);
        return new ResponseEntity<>(cart.toString(), getHttpHeaders(), HttpStatus.OK);
    }

    private final HttpHeaders httpHeaders = new HttpHeaders();
    protected HttpHeaders getHttpHeaders() {
        this.httpHeaders.add("Content-Type", "application/json");
        return this.httpHeaders;

    }

}
