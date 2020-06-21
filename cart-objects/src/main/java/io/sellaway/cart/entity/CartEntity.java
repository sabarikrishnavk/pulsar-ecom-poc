package io.sellaway.cart.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "cartmaster",
        indexes = { @Index(name = "cartid",  columnList="cartid", unique = true) }
                    )
public class CartEntity {

    @Id
    @Column(name="cartid")
    private String cartId;

    @Type(type="jsonb")
    @Column(name="cart", columnDefinition="jsonb")
    private byte[] cart;


}
