package com.gca.cart.models;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.*;


@Entity
public class ShopCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ElementCollection
    private Collection<Long> items;

    public ShopCart() {
    }

    public ShopCart(Collection<Long> items) {
        this.items = items;
    }

    public Long getId() {
        return this.id;
    }

    public Collection<Long> getItems() {
        return this.items;
    }

    public void setItems(Collection<Long> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ShopCart)) {
            return false;
        }
        ShopCart shopCart = (ShopCart) o;
        return Objects.equals(id, shopCart.id) && Objects.equals(items, shopCart.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", items='" + getItems() + "'" +
            "}";
    }

}