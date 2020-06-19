package io.sellaway.cart.objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartContact implements Serializable {

    String orderId;

    String firstName;
    String lastName;
    String email;
    String phone;

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
