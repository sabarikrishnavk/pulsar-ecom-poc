package io.sellaway.cart.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartPayment implements Serializable {
    String orderId;
    String tenderReference;
    TenderType tenderType;
    String details;
    Double amount;
}
