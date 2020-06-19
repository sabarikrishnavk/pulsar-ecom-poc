package io.sellaway.cart.objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartLineItem implements Serializable {
    String orderId;
    String lineNumber;
    String userId;
    String sku;
    Integer quantity;
    String addressId;
    String zipCode;
    String shippingMethod;

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
