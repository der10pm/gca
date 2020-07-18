package com.gca.cart;

import com.gca.cart.models.ShopCart;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<ShopCart, Long> {
    
}