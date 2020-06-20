package io.sellaway.cart.objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {

    String orderId;
    String userId;
    List<CartLineItem> lineItems;
    List<CartPayment> payments;
    CartContact contact;
    CartAddress shipAddress;
    CartAddress billAddress;
    CartSummary summary;

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String result= null;
        try {
            result = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;

    }
}
