package io.sellaway.cart.entity;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<CartEntity,String> {
}
